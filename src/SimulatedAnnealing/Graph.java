package SimulatedAnnealing;

import java.io.*;
import java.util.Scanner;

public class Graph {
    private int[][] matrix;

    public Graph() {
        this.matrix = null;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    //Function that prints a matrix with a graph to the screen
    public void printMatrix() {
        if(matrix != null) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j] >= 10) {
                        System.out.print(matrix[i][j] + " ");
                    } else {
                        System.out.print(matrix[i][j] + "  ");
                    }
                }
                System.out.println();
            }
        }
    }

    //Function that loads a graph from a file
    public void readFromFile(String nameOfFile) {
        try {
            File file = new File(nameOfFile);
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            scanner.nextLine();
            scanner.nextLine();
            String line = scanner.nextLine();
            String[] split = line.split(":");
            int numOfVertices = Integer.parseInt(split[1].trim());
            if(numOfVertices <= 0){
                return;
            }
            scanner.nextLine();
            scanner.nextLine();
            scanner.nextLine();
            matrix = new int[numOfVertices][numOfVertices];
            int i = 0;
            int j = 0;
            boolean condition = true;
            while (condition) {
                String currentRow = scanner.nextLine();
                String[] rowOfValues = currentRow.trim().split("\\s+");
                for (String num : rowOfValues) {
                    if (i == numOfVertices) {
                        break;
                    }
                    matrix[i][j] = Integer.parseInt(num);
                    j++;
                    if (j == numOfVertices) {
                        j = 0;
                        i++;
                    }
                }
                if(i  == numOfVertices) {
                    condition = false;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: '" + nameOfFile + "'");
        }
    }
}

