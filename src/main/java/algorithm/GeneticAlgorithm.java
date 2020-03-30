//class for performing genetic algorithm
package algorithm;

import model.Individual;
import model.Population;
import operator.Crossover;
import operator.Mutation;
import operator.Selection;
import utility.Loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GeneticAlgorithm {


    private static final int GENERATIONS = 600;
    private static final int POP_SIZE = 417;
    private static final Double MUTATION_PROB = 0.4;
    private static final Double CROSSOVER_CHANCE = 0.7;


    private Mutation mutation = new Mutation(MUTATION_PROB);
    private Selection selection = new Selection(POP_SIZE);
    private Crossover crossover = new Crossover();
    private Functions functions;
    private Random random = new Random();
    private Loader loader = new Loader();
    private List<List<List<Double>>> all_populations = new ArrayList<>();
    private List<List<Double>> one_run_populations = new ArrayList<>();
    private Population[] populations = new Population[GENERATIONS];


    public GeneticAlgorithm(Functions functions) {
        this.functions = functions;

    }

    public void evolution(Population population) {
        fillPopulationsArray(populations);
        //populations[0] = functions.generatePopulation(POP_SIZE);
        populations[0] = population;
        boolean TOURNAMENT = true;
        int t = 0;
        while (t < GENERATIONS - 1) {
            Double shortest_way = Double.POSITIVE_INFINITY;
            Double longest_way = Double.NEGATIVE_INFINITY;
            Double average_way = 0.0;
            while (populations[t + 1].getPopulation().size() < POP_SIZE - 1) {
                Individual parent1;
                Individual parent2;
                if (TOURNAMENT) {
                    parent1 = selection.tournamentSelection(populations[t]);
                    parent2 = selection.tournamentSelection(populations[t]);
                } else {
                    parent1 = selection.rouletteSelection(populations[t]);
                    parent2 = selection.rouletteSelection(populations[t]);
                }
                Individual child1;
                Individual child2;

                if (random.nextDouble() < CROSSOVER_CHANCE) {
                    List<Individual> children = crossover.partiallyMatchedCrossover(parent1, parent2);
                    child1 = children.get(0);
                    child2 = children.get(1);
                } else {
                    child1 = parent1;
                    child2 = parent2;
                }
                if (random.nextDouble() < MUTATION_PROB) {
                    child1 = mutation.inverseMutation(child1);

                }
                if (random.nextDouble() < MUTATION_PROB) {
                    child2 = mutation.inverseMutation(child2);

                }
                populations[t + 1].addIndividual(child1);
                populations[t + 1].addIndividual(child2);
                if (child1.getFitness() < shortest_way) {
                    shortest_way = child1.getFitness();
                }
                if (child2.getFitness() < shortest_way) {
                    shortest_way = child2.getFitness();
                }
                if (child1.getFitness() > longest_way) {
                    longest_way = child1.getFitness();
                }
                if (child2.getFitness() > longest_way) {
                    longest_way = child2.getFitness();
                }

            }
            t++;
            average_way = functions.findAverageFitness(populations[t]);
            List<Double> one_population_data = new ArrayList<>();
            one_population_data.add(shortest_way);
            one_population_data.add(average_way);
            one_population_data.add(longest_way);
            one_run_populations.add(one_population_data);

        }
        List<List<Double>> helper = one_run_populations.stream().collect(Collectors.toList());
        all_populations.add(helper);
        one_run_populations.clear();
    }

    public void saveMeasurementsToFile() {
        String data_to_save = "Generacja; best; average; worst;\n";
        int best_fitness_index = 0;
        int average_fitness_index = 1;
        int worst_fitness_index = 2;
        for (int i = 0; i < GENERATIONS - 1; i++) {
            List<Double> best_fitness = new ArrayList<>();
            List<Double> worst_fitness = new ArrayList<>();
            List<Double> average_fitness = new ArrayList<>();
            for (int j = 0; j < all_populations.size(); j++) {
                best_fitness.add(all_populations.get(j).get(i).get(best_fitness_index));
                average_fitness.add(all_populations.get(j).get(i).get(average_fitness_index));
                worst_fitness.add(all_populations.get(j).get(i).get(worst_fitness_index));
            }
            int average_fitness_final = functions.findAverageFitness(average_fitness).intValue();
            int worst_fitness_final = functions.findWorstFitness(worst_fitness).intValue();
            int best_fitness_final = functions.findBestFitness(best_fitness).intValue();
            data_to_save += i + 1 + "; " + best_fitness_final + "; " + average_fitness_final + "; " +
                    worst_fitness_final + ";\n";
        }
        loader.saveGenerationDataToFile(data_to_save);
    }

    private void fillPopulationsArray(Population[] populations) {
        for (int i = 0; i < populations.length; i++) {
            populations[i] = new Population();
        }
    }
}
