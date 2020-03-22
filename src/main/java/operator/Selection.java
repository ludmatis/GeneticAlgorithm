package operator;

import algorithm.Functions;
import model.Individual;
import model.Population;

import java.util.Random;

public class Selection {

    private Functions functions = new Functions();
    private static final Integer TOURNAMENT_INDIVIDUALS_AMOUNT = 5;
    private static final  Integer ELITES_AMOUNT = 10;
    public Selection() {
    }

    public Individual tournamentSelection(Population population){
        Random random = new Random();
        Population choosen_individuals = new Population();
        for(int i=0; i< TOURNAMENT_INDIVIDUALS_AMOUNT; i++){
            int individual_index = random.nextInt(population.getPopulation().size());
            choosen_individuals.addIndividual(population.getIndividual(individual_index));
        }
        Individual best_from_tournament =  functions.findBestIndividual(choosen_individuals);
        return best_from_tournament;
    }


}
