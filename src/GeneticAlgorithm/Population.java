package GeneticAlgorithm;


import java.util.Arrays;
import java.util.Random;

public class Population {

    private Chromosome[] chromosomes;
    private Graph graph;
    public void setGraph(Graph graph) {
        this.graph = graph;
    }
    public Chromosome[] getChromosomes() {
        return chromosomes;
    }

    // Adding an individual to the appropriate place in the table
    // and the evaluation function calculating its value - path cost
    public void setChromosomes(int[] route, int index) {
        Chromosome chromosome = new Chromosome();
        chromosome.setRoute(route);
        chromosome.setFitness(graph.costOfRoute(chromosome.getRoute()));
        chromosomes[index] = chromosome;
    }

    public void setChromosomes(Chromosome[] chromosomes) {
        this.chromosomes = chromosomes;
    }

    public void initializePopulation(int size) {
        this.chromosomes = new Chromosome[size];
    }

    // Random population initialization
    public void initialPopulation(int sizeOfPopulation) {
        this.chromosomes = new Chromosome[sizeOfPopulation];
        for(int i = 0; i < sizeOfPopulation; i++){
            Chromosome chromosome = new Chromosome();
            chromosome.generateRandomChromosome(graph.getMatrix().length);
            chromosome.setFitness(graph.costOfRoute(chromosome.getRoute()));
            chromosomes[i] = chromosome;
        }
    }

    // A function that sorts individuals according to their costs
    // (from lowest to highest path cost)
    public void sort() {
        Arrays.sort(this.chromosomes, (o1, o2) -> {
            if (o1.getFitness() < o2.getFitness()) {
                return -1;
            } else if (o1.getFitness() > o2.getFitness()) {
                return 1;
            }
            return 0;
        });
    }

    // Function 'mixing' individuals
    public void shuffle() {
        Random rnd = new Random();
        for (int i = chromosomes.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Chromosome a = chromosomes[index];
            chromosomes[index] = chromosomes[i];
            chromosomes[i] = a;
        }
    }
}
