package BruteForce;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteForce {

    // Initialization of BruteForce class fields
    private Graph graph;
    private ArrayList<Integer> bestRoute;
    private int bestRouteLength;
    private int numOfVertices = 0;

    // Assigning values for fields
    public void setGraph(Graph graph) {
        this.graph = graph;
        this.bestRoute = new ArrayList<>();
        this.bestRouteLength = Integer.MAX_VALUE;
        if(graph.getMatrix() != null){
            this.numOfVertices = graph.getMatrix().length;
        }
    }

    // The runBruteForce() method first checks whether the graph has been initialized correctly, then
    // initializes the array with elements on which we will perform permutations and calls the permute() method, which
    // will generate (n-1)! permutations
    public void runBruteForce() {
        if(graph.getMatrix() == null) {
            return;
        }
        int[] route = new int[numOfVertices];
        for (int i = 0; i < numOfVertices; i++) {
            route[i] = i;
        }
        //We start with 1 because we don't need n! permutations, only (n-1)!
        permute(route, 1, numOfVertices - 1);
    }

    //Recursive permute() function determining possible paths
    private void permute(int[] route, int start, int end) {
        if (start == end) {
            calculateTotalDistance(Arrays.stream(route).toArray());
        } else {
            for (int i = start; i <= end; i++) {
                if(start != i){
                    swap(route, start, i);
                }
                permute(route, start + 1, end);
                if(start != i)
                {
                    swap(route, start, i);
                }
            }
        }
    }

    // Auxiliary function for the permute function
    private void swap(int[] route, int i, int j) {
        int temp = route[i];
        route[i] = route[j];
        route[j] = temp;
    }

    // Function that calculates the cost of a given path and compares it to the current minimum path
    private void calculateTotalDistance(int[] route) {
        int routeLength = 0;
        for (int i = 0; i < numOfVertices - 1; i++) {
            routeLength += graph.getMatrix()[route[i]][route[i + 1]];
            if(routeLength >= bestRouteLength){
                i = numOfVertices;
            }
        }
        routeLength += graph.getMatrix()[route[numOfVertices - 1]][0];
        if (routeLength < bestRouteLength) {
            bestRouteLength = routeLength;
            bestRoute.clear();
            for (int vertex : route) {
                bestRoute.add(vertex);
            }
            bestRoute.add(0);
        }
    }

    //Function that prints the result to the screen
    public void printBestRoute() {
        System.out.println("Optimal path: " + bestRoute);
        System.out.println("Cost of the path: " + bestRouteLength);
    }
}


