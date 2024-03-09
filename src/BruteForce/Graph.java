package BruteForce;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Graph {
    private int[][] matrix;

    public Graph() {
        this.matrix = null;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    // Function that prints a matrix with a graph to the screen
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

    // Function for reading a graph from a file
    public void readFromFile(String nameOfFile) {
        try {
            File file = new File(nameOfFile);
            Scanner scanner = new Scanner(file);
            int numOfVertices = scanner.nextInt();
            if(numOfVertices <= 0){
                return;
            }
            scanner.nextLine();
            matrix = new int[numOfVertices][numOfVertices];
            for (int i = 0; i < numOfVertices; i++) {
                for (int j = 0; j < numOfVertices; j++) {
                    matrix[i][j] = scanner.nextInt();
                }
                if(i < (numOfVertices - 1)){
                    scanner.nextLine();
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: '" + nameOfFile + "'");
        }
    }

    //Function to randomly generate a graph
    public void randomGenerateGraph(int numOfVertices) {
        GraphGenerator.randomGraphGenerator(numOfVertices);
        readFromFile(GraphGenerator.graphGeneratorFileName);
    }
}
