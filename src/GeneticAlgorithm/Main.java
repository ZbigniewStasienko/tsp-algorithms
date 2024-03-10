package GeneticAlgorithm;

import java.util.Scanner;

public class Main {
    public static void menu() {
        boolean condition = true;
        Graph graph = new Graph();
        Scanner scanner = new Scanner(System.in);
        int option;
        int time = -1;
        int population = -1;
        int mutationOption = -1;
        int crossOption = -1;
        double mutationRate = -1.0;
        double crossRate = -1.0;
        while (condition) {
            System.out.println("\nTo load a graph from a file and display its contents, press '1'");
            System.out.println("To set the stop criterion (time) press '2'");
            System.out.println("To set the initial population size, press '3'");
            System.out.println("To set the mutation rate press '4'");
            System.out.println("To set the crossover rate, press '5'");
            System.out.println("To select the crossing method, press '6'");
            System.out.println("To select the mutation method, press '7'");
            System.out.println("To run the genetic algorithm press '8'");
            System.out.println("To return to the main menu, press '9'");
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
                    System.out.print("Enter the initial population size (e.g. 100): ");
                    population = scanner.nextInt();
                    break;
                case 4:
                    System.out.print("Enter the mutation rate (e.g. 0.01): ");
                    mutationRate = scanner.nextDouble();
                    break;
                case 5:
                    System.out.print("Enter the crossover coefficient (e.g. 0.8): ");
                    crossRate = scanner.nextDouble();
                    break;
                case 6:
                    System.out.println("To use the 'order crossover' method, press '1'");
                    System.out.println("To use the 'partially matched' method, press '2'");
                    System.out.print("Option: ");
                    crossOption = scanner.nextInt();
                    break;
                case 7:
                    System.out.println("To use the swap method, press '1'");
                    System.out.println("To use the invert method, press '2'");
                    System.out.print("Option: ");
                    mutationOption = scanner.nextInt();
                    break;
                case 8:
                    if(time != -1 && population != -1 && mutationRate != -1 && crossRate != -1 && crossOption != -1 && mutationOption != -1 && graph.getMatrix() != null){
                        GeneticAlgorithm ga = new GeneticAlgorithm(graph, time, population, crossOption, mutationOption, mutationRate, crossRate);
                        ga.geneticAlgorithm();
                        ga.printSolution();
                    } else {
                        System.out.println("Initialize all parameters before running the algorithm");
                    }
                    break;
                case 9:
                    condition = false;
                    break;
                default:
                    System.out.println("The number entered does not match any cases");
            }
        }
    }
}