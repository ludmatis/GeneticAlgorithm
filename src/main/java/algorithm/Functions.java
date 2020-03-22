package algorithm;

import model.City;
import model.Individual;
import model.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Functions {

    private List<City> cityList;
    private Random random;

    public Functions(List<City> cityList) {
        this.cityList = cityList.stream().map(City::new).collect(Collectors.toList());
        this.random = new Random();
    }

    public Functions() {
    }

    public Double countFitness(Individual individual) {
        List<City> cities = individual.getList_of_cities();
        Double distance = 0.0;
        for (int i = 1; i < cities.size(); i++) {
            City first_city = cities.get(i - 1);
            City second_city = cities.get(i);
            distance += countDistance(first_city, second_city);
        }
        Double last_distance = countDistance(cities.get(0), cities.get(cities.size()-1));
        distance+=last_distance;
        return distance;
    }

    private double countDistance(City first_city, City second_city) {
        return Math.sqrt(
                Math.pow(first_city.getX() - second_city.getX(), 2)
                        + Math.pow(first_city.getY() - second_city.getY(), 2));
    }

    public Individual generateIndividual() {
        int counter = cityList.size();
        List<City> cities = cityList.stream().collect(Collectors.toList());
        List<City> random_city_list = new ArrayList<>();
        while (counter > 0) {
            int random_index = random.nextInt(cities.size());
            random_city_list.add(cities.get(random_index));
            cities.remove(random_index);
            counter--;
        }
        return new Individual(random_city_list);
    }

    public Population generatePopulation(Integer population_size){
        List<Individual> individuals = new ArrayList<>();
        while (population_size>0){
            Individual individual = generateIndividual();
            individuals.add(individual);
            population_size--;
        }
        return new Population(individuals);
    }

    public Individual findBestIndividual(Population population){
        final Double[] fitness = {Double.POSITIVE_INFINITY};
        final Individual[] bestIndividual = {new Individual()};
        population.getPopulation().forEach(individual -> {
            if(individual.getFitness() < fitness[0]){
                fitness[0] = individual.getFitness();
                bestIndividual[0] = individual;
            }
        });
        return bestIndividual[0];
    }
}
