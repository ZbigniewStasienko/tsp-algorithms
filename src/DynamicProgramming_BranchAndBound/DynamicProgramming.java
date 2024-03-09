package DynamicProgramming_BranchAndBound;

import java.util.ArrayList;
import java.util.List;

public class DynamicProgramming {
    private final int startVertex = 0; // The starting vertex from which we explore the route
    private int n; // The number of vertices in the examined graph
    private int visitedAll; // The mask informing about visiting all vertices, e.g. for n=4, has the value 15 (1111)
    private int[][] distance; // Incident matrix for our graph
    private int[][] maskTable; // An array containing data for reconstructing the path and optimizing the algorithm
    private List<Integer> bestRoute; // A list of the best path vertices
    private int minCost; // Calculated minimum cost

    // By default, we set the constructor to null - a block against unwanted algorithm calls
    public DynamicProgramming() {
        distance = null;
    }

    // We set the values of the fields in our class based on the received graph.
    public void setGraph(Graph graph) {
        distance = graph.getMatrix();
        bestRoute = new ArrayList<>();
        bestRoute.add(startVertex);
        if(distance == null){
            return;
        }
        n = graph.getMatrix().length;
        visitedAll = (1 << n) - 1;
        maskTable = new int[1 << n][n];
        minCost = Integer.MAX_VALUE;
        maskTableInitialization();
    }

    // Recursive function implementing the algorithm using the top-down method.
    // We build paths from which we always choose the minimum possible,
    // and then refine them recursively until we reach the starting vertex
    private int tsp(int mask, int currentVertex) {
        // We have reached the end - we are leaving this level of nesting
        if (mask == visitedAll) {
            return distance[currentVertex][startVertex];
        }
        // We've solved this case before.
        // We return the appropriate cell in the array where we have the calculated path value for this case
        if (maskTable[mask][currentVertex] != -1) {
            return maskTable[mask][currentVertex];
        }
        // Cost for vertices not yet visited
        int cost = Integer.MAX_VALUE;

        // We iterate over all vertices and explore those we haven't visited yet
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                // We calculate the cost for the newly visited vertex.
                // Its value is the sum of the distance from the current vertex and the distance of the newly visited vertex to
                // starting vertex - we need to call functions recursively where the newly visited vertex is the currently examined vertex
                int calculatedCost = distance[currentVertex][i] + tsp(newMask, i);
                // If the calculated value is less than the current value
                // for the examined vertex, it becomes its value
                if (calculatedCost < cost) {
                    cost = calculatedCost;
                }
            }
        }
        // We update the array storing the minimum values for each mask and return the obtained distance
        return maskTable[mask][currentVertex] = cost;
    }

    // We initialize the array with the values for the masks
    private void maskTableInitialization() {
        for (int i = 0; i < (1 << n); i++) {
            for (int j = 0; j < n; j++) {
                maskTable[i][j] = -1;
            }
        }
    }

    // We initialize our algorithm with the appropriate vertex and mask
    public void runAlgorithm(){
        if(distance == null){
            return;
        }
        int mask = 1 << startVertex;
        minCost = tsp(mask,startVertex);
    }

    // Knowing the minimum cost, we repeat the path using the previously saved values in the maskTable array
    public void printResults() {
        int mask = 1 << startVertex;
        int pos = startVertex;
        while (mask != visitedAll) {
            int nextVertex = -1;
            int nextMask = 0;
            int bestCost = minCost;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) == 0) {
                    int newMask = mask | (1 << i);
                    int calculatedCost = distance[pos][i] + maskTable[newMask][i];
                    if (calculatedCost <= bestCost) {
                        bestCost = calculatedCost;
                        nextVertex = i;
                        nextMask = newMask;
                    }
                }
            }
            bestRoute.add(nextVertex);
            mask = nextMask;
            pos = nextVertex;
        }
        bestRoute.add(startVertex);
        System.out.println("Optimal path: " + bestRoute);
        System.out.println("Cost of the path: " + minCost);
        manuallyFreeMemory();
    }

    // Due to the large values of memory occupied mainly by maskTable arrays
    // we need to help Garbage Collector clean up memory after the algorithm finishes
    private void manuallyFreeMemory() {
        bestRoute = null;
        maskTable = null;
    }
}
