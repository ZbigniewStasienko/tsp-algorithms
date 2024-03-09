package GeneticAlgorithm;

import java.util.Arrays;

public class GeneticAlgorithm {
    // The graph for which we implement the algorithm
    private final Graph graph;
    // Number of elite individuals
    private final int elitismCount = 1;
    // Algorithm running time
    private final int time;
    private int tournamentSize = 8;
    private final int populationSize;
    // Crossing options (1 - 'OX', 2 - 'PMX')
    private final int crossOption;
    // Mutation options (1 - 'Swap', 2 - 'ReverseTransformation')
    private final int mutationOption;
    // Mutation probability coefficient
    private final double mutationRate;
    // Crossbreeding probability coefficient
    private final double crossRate;
    private Chromosome solution = null;

    public GeneticAlgorithm(Graph graph, int time, int populationSize, int crossOption, int mutationOption, double mutationRate, double crossRate) {
        this.graph = graph;
        this.time = time;
        this.populationSize = populationSize;
        this.crossOption = crossOption;
        this.mutationOption = mutationOption;
        this.mutationRate = mutationRate;
        this.crossRate = crossRate;
        if(populationSize < 50){
            this.tournamentSize = 2;
        }
    }

    public void geneticAlgorithm() {
        long startTime = System.currentTimeMillis();
        Population population = new Population();
        population.setGraph(graph);
        population.initialPopulation(populationSize);
        // The exit condition is the passage of time allocated to the algorithm
        while(true) {
            // Population evaluation - sorting individuals from best to worst
            population.sort();
            // Checking whether we are leaving the algorithm
            if ((System.currentTimeMillis() - startTime) > time) {
                break;
            }
            // Crossbreeding of individuals
            population.setChromosomes(crossoverPopulation(population));
        }
        solution = population.getChromosomes()[0];
    }

    // A tournament in which we randomly select a fixed number of individuals
    private Chromosome tournamentSelection(Population population) {
        // An auxiliary population containing only tournament participants
        Population tournament = new Population();
        tournament.setGraph(graph);
        tournament.initializePopulation(tournamentSize);
        // 'Mix' the population obtained at the input (a copy of the original population)
        population.shuffle();
        // We select the number of individuals specified by the tournament size parameter
        for (int i = 0; i < this.tournamentSize; i++) {
            tournament.setChromosomes(population.getChromosomes()[i].getRoute(), i);
        }
        tournament.sort();
        // We will select a winner from the tournament population
        return tournament.getChromosomes()[0];
    }
    // Function that creates a copy of the population - needed for the tournament to limit the number of sorting operations
    private Population copyOfPopulation(Population population) {
        Population copyOfPopulation = new Population();
        copyOfPopulation.setGraph(graph);
        copyOfPopulation.initializePopulation(populationSize);
        for (int i = 0; i < populationSize; i++) {
            copyOfPopulation.setChromosomes(population.getChromosomes()[i].getRoute(), i);
        }
        return copyOfPopulation;
    }

    // Function that performs crossbreeding of the entire population
    private Chromosome[] crossoverPopulation(Population population) {
        // We create a table with chromosomes for the new population
        Chromosome[] newPopulation = new Chromosome[populationSize];
        // We create a copy of the population needed to run the tournament
        Population shuffleCopy = copyOfPopulation(population);
        for(int i = 0; i < populationSize; i++) {
            // Iterating through the population we take another individual
            Chromosome parent1 = population.getChromosomes()[i];
            // When it is not an elite individual and the probability
            // crossover occurrences are met, crossover occurs
            if (this.crossRate > Math.random() && elitismCount < i) {
                // Using the tournament method, we choose the individual with whom we will breed
                Chromosome parent2 = tournamentSelection(shuffleCopy);
                // Check to avoid situations where the same individuals are duplicated.
                // In case two identical chromosomes are drawn for crossing,
                // one of them is thrown away and a new one is created randomly
                if(Arrays.equals(parent1.getRoute(), parent2.getRoute())) {
                    parent2.generateRandomChromosome(graph.getMatrix().length);
                    parent2.setFitness(graph.costOfRoute(parent2.getRoute()));
                }
                Crossover crossover = new Crossover();
                int [] crossedRoute;
                // We choose the order of parents with the same probability
                if(0.5 > Math.random()) {
                    crossedRoute = crossover.generateChild(parent1.getRoute(), parent2.getRoute(), crossOption);
                } else {
                    crossedRoute = crossover.generateChild(parent2.getRoute(), parent1.getRoute(), crossOption);
                }
                // Mutation of a new individual depending on the probability of mutations occurring in the population
                if(this.mutationRate > Math.random()) {
                    Mutation mutation = new Mutation();
                    crossedRoute = mutation.generateNewRoute(crossedRoute, mutationOption);
                }
                Chromosome newChromosome = new Chromosome();
                newChromosome.setRoute(crossedRoute);
                newChromosome.setFitness(graph.costOfRoute(crossedRoute));
                // Adding a new individual to the new population
                newPopulation[i] = newChromosome;
            } else {
                // When no crossing event has occurred, the original individual is added to the new population
                newPopulation[i] = parent1;
            }
        }
        return newPopulation;
    }
    // Function that prints the results to the screen
    public void printSolution(){
        System.out.println("Found path: ");
        solution.printChromosome();
        System.out.println("\nCost of the path: " + solution.getFitness());
    }

}
