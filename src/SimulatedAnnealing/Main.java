package SimulatedAnnealing;

import java.util.Scanner;

public class Main {
    public static void menu() {
        boolean condition = true;
        Graph graph = new Graph();
        Scanner scanner = new Scanner(System.in);
        int option;
        int time = -1;
        double coolingRate = -1;
        while (condition)
        {
            System.out.println("\nTo load a graph from a file, press '1'");
            System.out.println("To set the stop criterion (time) press '2'");
            System.out.println("To set the temperature change factor, press '3'");
            System.out.println("To run the algorithm implementing the simulated annealing method, press '4'");
            System.out.println("To return to the main menu, press '6'");
            System.out.print("Your choice: ");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.print("Enter a file name (remember to use the appropriate file extension): ");
                    scanner.nextLine();
                    String nameOfFile = scanner.nextLine();
                    graph.readFromFile(nameOfFile);
                    graph.printMatrix();
                    break;
                case 2:
                    System.out.print("Enter the algorithm running time (in seconds): ");
                    time = scanner.nextInt();
                    time = time * 1000;
                    break;
                case 3:
                    System.out.print("Enter the temperature change coefficient (e.g. 0.999): ");
                    coolingRate = scanner.nextDouble();
                    break;
                case 4:
                    if(time == -1 || coolingRate == -1){
                        System.out.println("Set the values before calling the algorithm");
                    } else {
                        SimulatedAnnealing sa = new SimulatedAnnealing(graph);
                        sa.simulateAnnealing(time, coolingRate);
                        sa.printResults();
                    }
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