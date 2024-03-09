// Klasa zaimplementowana w celu wyznaczenia mutacji dla danego chromosomu
// Wszystkie funkcje wewnatrz klasy pozostawiaja wierzchołek startowy i końcowy bez
// zmian tak, aby nie poszerzać bez potrzeby przestrzeni poszukiwań
package GeneticAlgorithm;

import java.util.Arrays;
import java.util.Random;

public class Mutation {
    private final Random random = new Random();

    // A function that selects which method we will use
    public int [] generateNewRoute(int [] route, int option) {
        int[] copy = Arrays.copyOf(route, route.length);
        if(option == 1){
            copy = swapCities(copy);
        } else {
            copy = reverseTransformation(copy);
        }
        return copy;
    }

    // A function that randomly selects two vertices and swaps them
    private int [] swapCities(int[] route) {
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

    // Function that randomly selects two items
    // in the chromosome and reversing the order of alleles in
    // the code substring specified in this way.
    // For example, the chromosome is 01234560, and we selected positions 2 and 5.
    // After transformation, the chromosome has the form 01543260
    private int [] reverseTransformation(int[] route) {
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
}
