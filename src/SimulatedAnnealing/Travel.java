package SimulatedAnnealing;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Travel {
    private final int [][] distances;

    public Travel(Graph graph) {
        distances = graph.getMatrix();
    }

    // A function that generates the initial path using the greedy method
    public int [] generateFirstTravel() {
        int startVertex = 0;
        int[] route = new int[distances.length + 1];
        int counter = 0;
        int j = 0;
        int i = 0;
        int numOfVertices = distances.length;
        int min = Integer.MAX_VALUE;
        List<Integer> visitedRouteList = new ArrayList<>();
        visitedRouteList.add(startVertex);
        int foundVert = -1;
        while (i < numOfVertices && j < numOfVertices) {
            if (counter >= numOfVertices - 1) {
                break;
            }
            if (j != i && !(visitedRouteList.contains(j))) {
                if (distances[i][j] < min) {
                    min = distances[i][j];
                    foundVert = j;
                }
            }
            j++;
            if (j == numOfVertices) {
                min = Integer.MAX_VALUE;
                visitedRouteList.add(foundVert);
                j = 0;
                i = foundVert;
                counter++;
            }
        }
        for(int a = 0; a < numOfVertices; a++) {
            route[a] = visitedRouteList.get(a);
        }
        route[numOfVertices] = visitedRouteList.get(0);
        return route;
    }

    // Function that calculates the cost of a given path
    public int getDistance(int [] route) {
        int cost = 0;
        for (int i = 0; i < (route.length-1); i++) {
            cost += distances[route[i]][route[i+1]];
        }
        return cost;
    }

    // Function that prints the path
    public void printRoute(int [] travel) {
        for(int i = 0; i < travel.length; i++) {
            System.out.print(travel[i] + " ");
        }
    }

    // Function to calculate the initial temperature
    public int calculateInitialTemperature(int [] route) {
        Neighbors neighbors = new Neighbors();
        double average = 0;
        int size = route.length - 1;
        int[] initialTravel;
        ArrayList<Integer> vertices = new ArrayList<>();
        for(int i = 1; i < size; i++){
            vertices.add(i);
        }
        initialTravel = generateRandomRoute(vertices);
        for(int i = 0; i < 10000; i++){
            int [] newTravel = neighbors.generateNewRoute(initialTravel);
            int difference = Math.abs(getDistance(initialTravel) - getDistance(newTravel));
            average += difference;
            initialTravel = generateRandomRoute(vertices);
        }
        average = average / 10000;
        double probability = 0.99;
        return (int) (-average / Math.log(probability));
    }

    // A function that generates a random path for a given set of vertices
    private int [] generateRandomRoute(ArrayList<Integer> vertices){
        ArrayList<Integer> copyTravel = new ArrayList<>(vertices);
        int size = copyTravel.size();
        int startVertex = 0;
        int[] returnTravel = new int[size + 2];
        returnTravel[0] = startVertex;
        for(int i = 1; i <= size; i++){
            int randomIndex = ThreadLocalRandom.current().nextInt(copyTravel.size());
            returnTravel[i] = copyTravel.get(randomIndex);
            copyTravel.remove(randomIndex);
        }
        returnTravel[size + 1] = startVertex;
        return returnTravel;
    }

}
