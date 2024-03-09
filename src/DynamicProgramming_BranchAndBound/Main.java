package DynamicProgramming_BranchAndBound;

import java.util.Scanner;

public class Main {
    public static void menu() {
        boolean condition = true;
        Graph graph = new Graph();
        DynamicProgramming dynamicProgramming = new DynamicProgramming();
        BranchAndBound branchAndBound = new BranchAndBound();
        Scanner scanner = new Scanner(System.in);
        long startTime;
        long time;
        int option;
        while (condition)
        {
            System.out.println("\nTo load a graph from a file, press '1'");
            System.out.println("To generate a random graph, press '2'");
            System.out.println("To display the graph on the screen, press '3'");
            System.out.println("To run the dynamic programming algorithm, press '4' (recommended max 25 vertices)");
            System.out.println("To run the algorithm implementing the B%B method, press '5' (recommended max 21 vertices)");
            System.out.println("To return to the main menu, press '6'");
            System.out.print("Your choice: ");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.print("Enter the file name (remember to add the appropriate file extension): ");
                    scanner.nextLine();
                    String nameOfFile = scanner.nextLine();
                    graph.readFromFile(nameOfFile);
                    break;
                case 2:
                    System.out.print("Enter the number of vertices: ");
                    int numOfVertices = scanner.nextInt();
                    graph.randomGenerateGraph(numOfVertices);
                    break;
                case 3:
                    graph.printMatrix();
                    break;
                case 4:
                    dynamicProgramming.setGraph(graph);
                    startTime = System.currentTimeMillis();
                    dynamicProgramming.runAlgorithm();
                    time = System.currentTimeMillis() - startTime;
                    dynamicProgramming.printResults();
                    System.out.println("The algorithm execution time was: " + time + " ms");
                    break;
                case 5:
                    System.out.println("!!!The algorithm will automatically terminate after 5 minutes and return the current best result!!!");
                    branchAndBound.setGraph(graph);
                    startTime = System.currentTimeMillis();
                    branchAndBound.runAlgorithm();
                    time = System.currentTimeMillis() - startTime;
                    branchAndBound.printResults();
                    System.out.println("The algorithm execution time was: " + time + " ms");
                    break;
                case 6:
                    condition = false;
                    break;
                default:
                    System.out.println("The number entered does not match any cases");
            }
        }
    }
}