import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean condition = true;
        int option;
        Scanner scanner = new Scanner(System.in);
        while (condition)
        {
            System.out.println("\nTo enter Brute Force menu, press '1'");
            System.out.println("To enter Dynamic Programming/Branch and Bound menu, press '2'");
            System.out.println("To enter Simulated Annealing menu, press '3'");
            System.out.println("To enter Genetic Algorithm menu, press '4'");
            System.out.println("To exit, press '5'");
            System.out.print("Your choice: ");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    BruteForce.Main.menu();
                    break;
                case 2:
                    DynamicProgramming_BranchAndBound.Main.menu();
                    break;
                case 3:
                    SimulatedAnnealing.Main.menu();
                    break;
                case 4:
                    GeneticAlgorithm.Main.menu();
                    break;
                case 5:
                    condition = false;
                    break;
                default:
                    System.out.println("The number entered does not match any cases");
            }
        }
        scanner.close();
    }
}