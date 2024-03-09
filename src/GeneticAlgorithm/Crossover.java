package GeneticAlgorithm;

import java.util.*;

public class Crossover {
    private static final Random random = new Random();

    // A function that selects which method we will use
    public int [] generateChild(int [] parent1, int [] parent2, int option) {
        int[] copy1 = Arrays.copyOf(parent1, parent1.length);
        int[] copy2 = Arrays.copyOf(parent2, parent1.length);
        int[] child;
        if(option == 1){
            child = crossOX(copy1, copy2);
        } else {
            child = crossPMX(copy1, copy2);
        }
        return child;
    }

    // OX crossing method - (order crossover)
    private int[] crossOX(int[] parent1, int[] parent2) {
        int[] child = new int[parent1.length];

        child[0] = 0;
        child[child.length - 1] = 0;

        int start = random.nextInt(parent1.length - 2) + 1;
        int end = random.nextInt(parent1.length - 2) + 1;

        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }

        if (end + 1 - start >= 0) {
            System.arraycopy(parent1, start, child, start, end + 1 - start);
        }

        int currentIndex = (end + 1) % (parent1.length-1);
        int parentIndex = currentIndex;
        for (int i = 1; i < parent2.length - 1; i++) {
            if(currentIndex == 0){
                currentIndex = 1;
            }
            if(parentIndex == 0){
                parentIndex = 1;
            }
            if (!contains(child, parent2[parentIndex])) {
                child[currentIndex] = parent2[parentIndex];
                currentIndex = (currentIndex + 1) % (parent1.length-1);
            }
            parentIndex = (parentIndex + 1) % (parent1.length-1);
        }

        return child;
    }

    // PMX (partially matched) crossing method
    private int[] crossPMX(int[] array1, int[] array2) {
        int[] parent1 = new int[array1.length - 2];
        System.arraycopy(array1, 1, parent1, 0, array1.length - 2);

        int[] parent2 = new int[array2.length - 2];
        System.arraycopy(array2, 1, parent2, 0, array2.length - 2);

        int length = parent1.length;
        int[] child = new int[length];

        int start = random.nextInt(parent1.length);
        int end = random.nextInt(parent1.length);

        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }

        Map<Integer, Integer> mapping = new HashMap<>();
        Set<Integer> usedVertices = new HashSet<>();

        for (int i = start; i <= end; i++) {
            child[i] = parent1[i];
            mapping.put(parent1[i], parent2[i]);
            usedVertices.add(parent1[i]);
        }

        for (int i = 0; i < length; i++) {
            if (i < start || i > end) {
                int value = parent2[i];
                while (mapping.containsKey(value)) {
                    value = mapping.get(value);
                }
                while (usedVertices.contains(value)) {
                    value = parent2[value];
                }
                child[i] = value;
                usedVertices.add(value);
            }
        }
        int[] fixedChild = new int[child.length + 2];
        fixedChild[0] = 0;
        System.arraycopy(child, 0, fixedChild, 1, child.length);
        fixedChild[fixedChild.length - 1] = 0;
        return fixedChild;
    }

    // Helper function for the cross1 method
    private boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }
}
