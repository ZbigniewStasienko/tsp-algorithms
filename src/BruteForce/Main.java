package BruteForce;

import java.util.Scanner;

public class Main {
    public static void menu() {
        boolean condition = true;
        Graph graph = new Graph();
        BruteForce bruteForce = new BruteForce();
        Scanner scanner = new Scanner(System.in);
        long startTime;
        long time;
        int option;
        while (condition)
        {
            System.out.println("\nTo load a graph from a file, press '1'");
            System.out.println("To generate a random graph, press '2'");
            System.out.println("To display the graph on the screen, press '3'");
            System.out.println("To run the brute force algorithm, press '4'");
            System.out.println("To return to main menu, press '5'");
            System.out.print("Your choice: ");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.print("Enter a file name (remember to use the appropriate file extension): ");
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
                    bruteForce.setGraph(graph);
                    startTime = System.currentTimeMillis();
                    bruteForce.runBruteForce();
                    time = System.currentTimeMillis() - startTime;
                    bruteForce.printBestRoute();
                    System.out.println("The algorithm execution time was: " + time + " ms");
                    break;
                case 5:
                    condition = false;
                    break;
                default:
                    System.out.println("The number entered does not match any cases");
            }
        }
    }
}