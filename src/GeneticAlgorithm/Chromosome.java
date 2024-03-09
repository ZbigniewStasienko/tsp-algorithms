package GeneticAlgorithm;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Chromosome implements Comparable {
    private int[] route;
    private int fitness;
    public int[] getRoute() {
        return route;
    }

    public void setRoute(int[] route) {
        this.route = route;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public void printChromosome() {
        for(int i = 0; i < route.length; i++) {
            System.out.print(route[i] + " ");
        }
    }

    // A function that generates a random chromosome (route)
    public void generateRandomChromosome(int sizeOfChromosome){
        sizeOfChromosome--;
        ArrayList<Integer> vertices = new ArrayList<>(sizeOfChromosome);
        for(int i = 1; i <= sizeOfChromosome; i++) {
            vertices.add(i);
        }
        int startVertex = 0;
        route = new int[sizeOfChromosome + 2];
        route[0] = startVertex;
        for(int i = 1; i <= sizeOfChromosome; i++){
            int randomIndex = ThreadLocalRandom.current().nextInt(vertices.size());
            route[i] = vertices.get(randomIndex);
            vertices.remove(randomIndex);
        }
        route[sizeOfChromosome + 1] = startVertex;
    }

    // Overloaded operator comparing two objects
    // so that objects are compared according to their costs
    @Override
    public int compareTo(Object o) {
        Chromosome chromosome = (Chromosome) o;
        if(this.fitness < chromosome.getFitness())
            return 1;
        else if(this.fitness > chromosome.getFitness())
            return -1;
        else
            return 0;
    }

}
