//class for performing random algorithm search
package algorithm;

import model.Individual;
import model.Population;
import utility.Loader;

import java.util.ArrayList;
import java.util.List;

public class RandomAlgorithm {


    private Functions functions;

    public RandomAlgorithm(Functions functions) {
        this.functions = functions;
    }

    public List<Double> runRandomAlgorithm(){
        int RUNS = 1000000;
        List<Double> fitnessess = new ArrayList<>();
        double best = Double.POSITIVE_INFINITY;
        for(int i=0; i<RUNS; i++){
            Individual individual = functions.generateIndividual();
            double fitness = individual.getFitness();
            if(fitness < best){
                best = fitness;
            }
            fitnessess.add(fitness);
        }
        Double average = functions.findAverageFitness(fitnessess);
        Double worst = functions.findWorstFitness(fitnessess);
        Double standard_deviation = functions.countStandardDeviation(fitnessess);
        List<Double> to_return = new ArrayList<>();
        to_return.add(best);
        to_return.add(average);
        to_return.add(worst);
        to_return.add(standard_deviation);
        return to_return;
    }

    private void safeMeasurementsToFile(Population population){
        Loader loader = new Loader();
        String data_to_save = "Fitness;\n";
        for(int i=0; i<population.getPopulation().size(); i++){
            data_to_save += population.getIndividual(i).getFitness().intValue() + ";\n";
        }
        loader.saveRandomAlgorithmDataToFIle(data_to_save);
    }
}


