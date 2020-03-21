package algorithm;

import model.Population;
import operator.Crossover;
import operator.Mutation;
import operator.Selection;

public class GeneticAlgorithm {

    private static final Integer POP_SIZE = 100;
    private static final Double MUTATION_PROB = 0.5;
    private static final Double CROSSOVER_CHANCE = 0.5;

    private Population population;
    private Population descendands;

    private Mutation mutation = new Mutation(MUTATION_PROB);
    private Selection selection = new Selection();
    private Crossover crossover = new Crossover(CROSSOVER_CHANCE);
}
