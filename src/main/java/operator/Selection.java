//class for performing selection operations
package operator;

import algorithm.Functions;
import model.Individual;
import model.Population;

import java.util.Random;

public class Selection {

    private Functions functions = new Functions();
    //procentowa wartość osobników
    private static final Double TOURNAMENT_INDIVIDUALS_AMOUNT = 0.05;
    private int pop_size;

    public Selection(int pop_size) {
        this.pop_size = pop_size;
    }

    public Individual tournamentSelection(Population population) {
        Random random = new Random();
        Population choosen_individuals = new Population();
        int real_tour_amount = (int) (TOURNAMENT_INDIVIDUALS_AMOUNT * pop_size);
        for (int i = 0; i < real_tour_amount; i++) {
            int individual_index = random.nextInt(population.getPopulation().size());
            choosen_individuals.addIndividual(population.getIndividual(individual_index));
        }
        return functions.findBestIndividual(choosen_individuals);
    }

    //implemented with help of
    //https://www.cse.huji.ac.il/~ai/projects/old/tsp2.pdf
    public Individual rouletteSelection(Population population) {
        Random random = new Random();
        final long[] sum_of_fitnesses = {0L};
        population.getPopulation().forEach(individual -> sum_of_fitnesses[0] += individual.getFitness().longValue());
        Double r = random.nextDouble();
        double sumOfProb = 0.0;
        int choosen_index = 0;
        for (int i = 0; i < population.getPopulation().size(); i++) {
            double prob_of_individual = population.getIndividual(i).getFitness() / sum_of_fitnesses[0];
            if (r < (sumOfProb + prob_of_individual) && r > sumOfProb) {
                choosen_index = i;
                break;
            } else {
                sumOfProb += prob_of_individual;
            }
        }
        return population.getIndividual(choosen_index);
    }


}
