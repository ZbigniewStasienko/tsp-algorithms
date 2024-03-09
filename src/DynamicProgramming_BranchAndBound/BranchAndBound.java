package DynamicProgramming_BranchAndBound;

public class BranchAndBound {
    private final int startVertex = 0; // The starting vertex from which we explore the route
    private int n; // The number of vertices in the examined graph
    private int[][] distance; // Incident matrix for graph
    private int[] finalPath; // An array containing the current best path
    private boolean[] visited; // An array informing us which vertices are visited
    private boolean exitCondition = false; // Variable to determine if algorithm ended by timeout
    private long startTime; // A variable that informs us about the start of the algorithm execution
    private final long maximumTime = 300000; // Maximum operating time (300,000ms = 300s = 5min)
    private int currentBest; // Currently the best cost

    // By default, we set the constructor to null - a block against unwanted algorithm calls.
    public BranchAndBound() {
        distance = null;
    }

    // We set the values of the fields in our class based on the received graph.
    public void setGraph(Graph graph) {
        distance = graph.getMatrix();
        if(distance == null) {
            return;
        }
        n = graph.getMatrix().length;
        finalPath = new int[n];
        visited = new boolean[n];
        currentBest = initialUpperBound();
        exitCondition = false;
        initialPath();
    }

    // We initialize the cost of thr initial upper bound.
    // I decided to examine the path that would be checked last
    // (we know the order of visiting vertices - DFS method).
    private int initialUpperBound() {
        int initialBound = distance[0][n-1];
        for(int i = n-1; i > 0; i--){
            initialBound += distance[i][i-1];
        }
        return initialBound;
    }

    // We initiate the best route
    private void initialPath () {
        finalPath[0] = startVertex;
        for(int i = 1; i < n; i++) {
            finalPath[i] = (startVertex + i) % n;
        }
    }

    // Function to copy the calculated path if it turns out to be better than the current boundary
    private void copyToFinal(int[] currPath) {
        if (n >= 0){
            System.arraycopy(currPath, 0, finalPath, 0, n);
        }
    }

    // Recursive function to calculate subsequent costs of our route.
    // Takes as a parameter the "level" of the vertices we will examine,
    // the course of the current path and its cost.
    private void tsp(int level, int[] currPath, int currWeight) {
        // Measure whether the operation time has already expired
        if((System.currentTimeMillis() - startTime) > maximumTime) {
            exitCondition = true;
            return;
        }
        // We have reached the end of our graph - we check the cost of returning to the initial vertex.
        // When the total cost turns out to be less than the current minimum, we set the calculated value as our minimum
        if (level == n) {
            int currRes = currWeight + distance[currPath[level - 1]][currPath[0]];
            if (currRes < currentBest) {
                copyToFinal(currPath);
                currentBest = currRes;
            }
            return;
        }
        // Of all the vertices we traverse the unvisited ones one by one
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                currPath[level] = i;
                visited[i] = true;
                // We count the cost from the currently explored vertex to the found, unvisited vertex.
                // When it is less than the cost of the current minimum path, we go "level" lower
                // and our found vertex becomes the one currently being examined.
                int nextWeight = currWeight + distance[currPath[level - 1]][i];
                if(nextWeight < currentBest) {
                    tsp(level + 1, currPath, nextWeight);
                }
                // When we finish examining the paths from the found vertex
                // then we deselect it and mark it as unvisited
                currPath[level] = -1;
                visited[i] = false;
            }
        }
    }

    // The function calling the algorithm marks the starting vertex as visited and starts examining subsequent vertices
    public void runAlgorithm() {
        if(distance == null) {
            return;
        }
        startTime = System.currentTimeMillis();
        int[] currPath = new int[n];
        currPath[0] = startVertex;
        visited[startVertex] = true;
        tsp(1, currPath, 0);
    }

    // A function that returns the result of our algorithm
    public void printResults() {
        // When we have finished the operation within the maximum time, we send an appropriate message
        if(exitCondition){
            System.out.println("!!ATTENTION!!");
            System.out.println("Algorithm execution has ended - its maximum running time has been exceeded");
            System.out.println("This is CURRENTLY the best path - maybe there is a better path");
        }
        System.out.print("Optimal path: ");
        System.out.print("[");
        for (int i = 0; i < n; i++) {
            System.out.print(finalPath[i] + ", ");
        }
        System.out.println(startVertex + "]");
        System.out.println("Cost of the path: " + currentBest);
    }
}
