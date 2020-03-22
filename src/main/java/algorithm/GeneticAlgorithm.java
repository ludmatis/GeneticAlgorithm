package algorithm;

import model.Individual;
import model.Population;
import operator.Crossover;
import operator.Mutation;
import operator.Selection;

import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {


    private static final int GENERATIONS = 10000;
    private static final int POP_SIZE = 100;
    private static final Double MUTATION_PROB = 0.1;
    private static final Double CROSSOVER_CHANCE = 0.7;


    private Mutation mutation = new Mutation(MUTATION_PROB);
    private Selection selection = new Selection();
    private Crossover crossover = new Crossover();
    private Functions functions;
    private Random random = new Random();

    private Population[] populations = new Population[GENERATIONS];


    public GeneticAlgorithm(Functions functions) {
        this.functions = functions;
    }

    public Double evolution() {
        boolean PMX = true;
        fillPopulationsArray(populations);
        populations[0] = functions.generatePopulation(POP_SIZE);
        Double shortest_way = Double.POSITIVE_INFINITY;
        Individual bestIndividual = null;
        int t = 0;
        while (t < GENERATIONS - 1) {
            while (populations[t + 1].getPopulation().size() < POP_SIZE - 1) {
                Individual parent1 = selection.tournamentSelection(populations[t]);
                Individual parent2 = selection.tournamentSelection(populations[t]);
                //System.out.println("parent1: " +parent1);
                //System.out.println("parent2: " + parent2);
                Individual child1;
                Individual child2;
                if (PMX) {
                    if (random.nextDouble() < CROSSOVER_CHANCE) {
                        List<Individual> children = crossover.partiallyMatchedCrossover(parent1, parent2);
                        child1 = children.get(0);
                        child2 = children.get(1);
                    } else {
                        child1 = parent1;
                        child2 = parent2;
                    }
                    if(random.nextDouble() < MUTATION_PROB){
                        child1 = mutation.inverseMutation(child1);

                    }
                    if(random.nextDouble() < MUTATION_PROB){
                        child2 = mutation.inverseMutation(child2);

                    }
                    populations[t + 1].addIndividual(child1);
                    populations[t + 1].addIndividual(child2);
                    if (child1.getFitness() < shortest_way) {
                        shortest_way = child1.getFitness();
                        bestIndividual = child1;
                    }
                    if (child2.getFitness() < shortest_way) {
                        shortest_way = child2.getFitness();
                        bestIndividual = child2;
                    }
                } else {
                    if (random.nextDouble() < CROSSOVER_CHANCE) {
                        child1 = crossover.orderedCrossover(parent1, parent2);
                    } else {
                        child1 = parent1;
                    }
                    child1 = mutation.inverseMutation(child1);
                    populations[t + 1].addIndividual(child1);
                    if (child1.getFitness() < shortest_way) {
                        shortest_way = child1.getFitness();
                        bestIndividual = child1;
                    }
                }
            }
            shortest_way = functions.findBestIndividual(populations[t]).getFitness();
            System.out.println(functions.findBestIndividual(populations[t]));
            t++;
        }
        return shortest_way;
    }

    private void fillPopulationsArray(Population[] populations) {
        for (int i = 0; i < populations.length; i++) {
            populations[i] = new Population();
        }
    }
}
