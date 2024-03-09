package DynamicProgramming_BranchAndBound;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GraphGenerator {
    //Name of the file to which the graph will be saved
    public static String graphGeneratorFileName = "randomGraphGeneratorFile.txt";

    //Function printing a randomly generated graph with weights in the range <1.50> to the file
    public static void randomGraphGenerator(int numOfVertices) {
        if(numOfVertices <= 0){
            return;
        }
        Random rand = new Random();
        try {
            FileWriter writer = new FileWriter(graphGeneratorFileName);
            writer.write(numOfVertices + "\n");
            for (int i = 0; i < numOfVertices; i++) {
                for (int j = 0; j < numOfVertices; j++) {
                    if (i == j) {
                        writer.write("-1 ");
                    } else {
                        writer.write((rand.nextInt(50) + 1)  + " ");
                    }
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error during creating the file: " + e.getMessage());
        }
    }
}
