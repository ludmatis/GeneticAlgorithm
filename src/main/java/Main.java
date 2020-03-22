import algorithm.Functions;
import algorithm.GeneticAlgorithm;
import algorithm.GreedyAndRandomAlghoritm;
import model.City;
import model.Individual;
import model.Population;
import operator.Crossover;
import operator.Mutation;
import utility.CityCreator;
import utility.Loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Loader loader = new Loader();
        loader.init();

        BufferedReader bufferedReader;
        bufferedReader = loader.getBufferedReader();

        String[] tsp_data = new String[1001];
        int index = 0;
        String read_line = "";

        while ((read_line = bufferedReader.readLine()) != null) {
            tsp_data[index] = read_line;
            index++;
        }

        List<City> cityList;
        CityCreator cityCreator = new CityCreator(tsp_data);
        cityList = cityCreator.generateCities();

        //cityList.forEach(System.out::println);

        GreedyAndRandomAlghoritm greedyAlghoritm = new GreedyAndRandomAlghoritm(cityList);

        //Population population1 = greedyAlghoritm.runGreedyAlgorithm();
        //City city = cityList.get(0);
        //City city1 = cityList.get(1);
        //System.out.println(greedyAlghoritm.countDistance(city,city1 ));
        //greedyAlghoritm.runRandomAlgorithm();
        Functions functions = new Functions(cityList);
        Individual individual = functions.generateIndividual();
        //System.out.println(individual);
        Population population = functions.generatePopulation(100);
        //System.out.println(population);
        Individual bestIndividual = functions.findBestIndividual(population);
        System.out.println(bestIndividual);
        //Mutation mutation = new Mutation(0.5);
        //Individual swapped = mutation.swapMutation(bestIndividual);
       // System.out.println(swapped);
        //Individual inversed = mutation.inverseMutation(bestIndividual);
        //System.out.println(inversed);
        Crossover crossover = new Crossover();
//        Individual individuals = crossover.orderedCrossover(individual, bestIndividual);
//        for(int i =0; i< individuals.length; i++){
//            System.out.print(individuals[i].getId() + " ");
//        }
//        System.out.println(individuals);
//        List<Individual> individuals = crossover.partiallyMatchedCrossover(individual, bestIndividual);
//        individuals.forEach(System.out::println);
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(functions);
        Double bestIndividualOK = geneticAlgorithm.evolution();
        System.out.println(bestIndividualOK);
        Individual individual1 = crossover.orderedCrossover(bestIndividual, individual);
        //System.out.println(individual1);
    }

}
