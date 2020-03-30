//class for general operations on individuals
package algorithm;

import model.City;
import model.Individual;
import model.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Functions {

    private List<City> cityList;

    public Functions(List<City> cityList) {
        this.cityList = cityList.stream().map(City::new).collect(Collectors.toList());
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
        Double last_distance = countDistance(cities.get(0), cities.get(cities.size() - 1));
        distance += last_distance;
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
        Collections.shuffle(cities);
        return new Individual(cities);
    }

    public Population generatePopulation(Integer population_size) {
        List<Individual> individuals = new ArrayList<>();
        while (population_size > 0) {
            Individual individual = generateIndividual();
            individuals.add(individual);
            population_size--;
        }
        return new Population(individuals);
    }

    public Individual findBestIndividual(Population population) {
        final Double[] fitness = {Double.POSITIVE_INFINITY};
        final Individual[] bestIndividual = {new Individual()};
        population.getPopulation().forEach(individual -> {
            if (individual.getFitness() < fitness[0]) {
                fitness[0] = individual.getFitness();
                bestIndividual[0] = individual;
            }
        });
        return bestIndividual[0];
    }

    public Individual findWorstIndividual(Population population) {
        final Double[] fitness = {Double.NEGATIVE_INFINITY};
        final Individual[] worstIndividual = {new Individual()};
        population.getPopulation().forEach(individual -> {
            if (individual.getFitness() > fitness[0]) {
                fitness[0] = individual.getFitness();
                worstIndividual[0] = individual;
            }
        });
        return worstIndividual[0];
    }

    public Double findAverageFitness(Population population) {
        List<Double> all_fitnesses = new ArrayList<>();
        Double average;
        population.getPopulation().forEach(individual -> all_fitnesses.add(individual.getFitness()));
        final Double[] all_fitnesses_sum = {0.0};
        all_fitnesses.forEach(fitness -> all_fitnesses_sum[0] += fitness);
        average = all_fitnesses_sum[0] / all_fitnesses.size();
        return average;
    }

    public Double findAverageFitness(List<Double> fitness_list) {
        double average;
        final double[] all_fitness_sum = {0};
        fitness_list.forEach(fitness -> all_fitness_sum[0] += fitness);
        average = all_fitness_sum[0] / fitness_list.size();
        return average;
    }

    public Double findBestFitness(List<Double> fitness_list) {
        final double[] best_fitness = {Double.POSITIVE_INFINITY};

        fitness_list.forEach(fitness -> {
            if (fitness < best_fitness[0]) {
                best_fitness[0] = fitness;
            }
        });
        return best_fitness[0];
    }

    public Double findWorstFitness(List<Double> fitness_list) {
        final double[] worst_fitness = {Double.NEGATIVE_INFINITY};

        fitness_list.forEach(fitness -> {
            if (fitness > worst_fitness[0]) {
                worst_fitness[0] = fitness;
            }
        });
        return worst_fitness[0];
    }

    public Double countStandardDeviation(List<Double> fitnesses) {
        double[] fitnesses_array = new double[fitnesses.size()];
        for (int i = 0; i < fitnesses_array.length; i++) {
            fitnesses_array[i] = fitnesses.get(i);
        }
        return (new StandardDeviation()).calculateSD(fitnesses_array);
    }

    public List<City> getCityList() {
        return cityList;
    }
}


class StandardDeviation {

    public double calculateSD(double numArray[]) {
        double mean;
        double sum = 0.0;
        double std = 0.0;
        for (double city : numArray
        ) {
            sum += city;
        }
        mean = sum / numArray.length;
        for (double city : numArray) {
            std += (city - mean) * (city - mean);
        }
        return Math.sqrt(std / numArray.length);
    }

}