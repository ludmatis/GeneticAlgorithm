import algorithm.Functions;
import algorithm.GeneticAlgorithm;
import algorithm.GreedyAlgorithm;
import algorithm.RandomAlgorithm;
import model.City;
import model.Population;
import utility.CityCreator;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final boolean GENETIC = false;
    private static final boolean RANDOM = true;
    private static final boolean GREEDY = false;

    public static void main(String[] args) throws IOException {


        CityCreator cityCreator = new CityCreator();
        List<City> cityList;
        cityList = cityCreator.generateCities();
        Functions functions = new Functions(cityList);
        GreedyAlgorithm greedyAlghoritm = new GreedyAlgorithm(cityList);
        RandomAlgorithm x = new RandomAlgorithm(functions);
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(functions);

        if (GREEDY) {
            Population population1 = greedyAlghoritm.runGreedyAlgorithm();
            //Individual okk = functions.findBestIndividual(population1);
            //System.out.println(okk);
            List<Double> results = greedyAlghoritm.resultsOfGreedyAlgorithm();
            System.out.println("Best: " + results.get(0));
            System.out.println("Average: " + results.get(1));
            System.out.println("Worst: " + results.get(2));
            System.out.println("Standard deviation: " + results.get(3));
        }
        if (RANDOM) {
            List<Double> results = x.runRandomAlgorithm();
            System.out.println("Best: " + results.get(0));
            System.out.println("Average: " + results.get(1));
            System.out.println("Worst: " + results.get(2));
            System.out.println("Standard deviation: " + results.get(3));
        }
        if (GENETIC) {
            for (int i = 1; i < 11; i++) {
                geneticAlgorithm.evolution(greedyAlghoritm.runGreedyAlgorithm());
            }
            geneticAlgorithm.saveMeasurementsToFile();
        }


    }

}
