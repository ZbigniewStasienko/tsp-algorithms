package SimulatedAnnealing;

import java.util.Arrays;
import java.util.Random;

public class Neighbors {
    private Random random = new Random();

    // A function that selects one of the three options for determining the neighborhood
    public int [] generateNewRoute(int [] route) {
        int[] copy = Arrays.copyOf(route, route.length);
        double rand = random.nextDouble();
        if(rand < 0.33){
            copy = swapCities(copy);
        } else if (rand < 0.66) {
            copy = reverseTransformation(copy);
        } else {
            copy = transportTransformation(copy);
        }
        return copy;
    }

    // A function that randomly selects two vertices and swaps them
    private int [] swapCities(int route[]) {
        int numOfVertices = route.length;
        int a = random.nextInt(numOfVertices - 2) + 1;
        int b = random.nextInt(numOfVertices - 2) + 1;
        boolean condition = true;
        while(condition) {
            if(a != b) {
                condition = false;
            } else {
                b = random.nextInt(numOfVertices - 2) + 1;
            }
        }
        int prevA = route[a];
        route[a] = route[b];
        route[b] = prevA;
        return route;
    }

    // Function that reverses the path between two selected vertices
    // For example, the path is 01234560, and we selected vertices 2 and 5.
    // After transformation, the path becomes 01543260
    private int [] reverseTransformation(int route[]) {
        int numOfElements = route.length;
        int start = random.nextInt(numOfElements/2 - 1 ) + 1;
        int end;
        if(numOfElements % 2 == 0) {
            end = random.nextInt(numOfElements / 2 - 1 ) + numOfElements/2;
        } else {
            end = random.nextInt(numOfElements / 2) + numOfElements/2;
        }

        while (start < end) {
            int temp = route[start];
            route[start] = route[end];
            route[end] = temp;
            start++;
            end--;
        }
        return route;
    }

    // A function that "cuts" a selected part of the path and pastes the given fragment in a random place.
    // For example, the path is 01234560, and we selected vertices 3 and 5.
    // We drew index 1.
    // After transformation, the path becomes 03451260
    private int [] transportTransformation(int route[]) {
        int numOfElements = route.length;
        int start = random.nextInt(numOfElements / 2 - 1) + 1;
        int end;
        if (numOfElements % 2 == 0) {
            end = random.nextInt(numOfElements / 2 - 1) + numOfElements / 2;
        } else {
            end = random.nextInt(numOfElements / 2) + numOfElements / 2;
        }

        int[] segment = Arrays.copyOfRange(route, start, end + 1);
        int[] beforeSegment = Arrays.copyOfRange(route, 0, start);
        int[] afterSegment = Arrays.copyOfRange(route, end + 1, numOfElements);

        int[] mergedArray = new int[beforeSegment.length + afterSegment.length];

        System.arraycopy(beforeSegment, 0, mergedArray, 0, beforeSegment.length);
        System.arraycopy(afterSegment, 0, mergedArray, beforeSegment.length, afterSegment.length);

        int insertIndex = random.nextInt(numOfElements - (end - start + 2)) + 1;

        int[] combinedArray = new int[numOfElements];

        System.arraycopy(mergedArray, 0, combinedArray, 0, insertIndex);
        System.arraycopy(segment, 0, combinedArray, insertIndex, segment.length);
        System.arraycopy(mergedArray, insertIndex, combinedArray, insertIndex + segment.length, mergedArray.length - insertIndex);

        System.arraycopy(combinedArray, 0, route, 0, numOfElements);
        return route;
    }
}
