//class for performing greedy algorithm search
package algorithm;

import model.City;
import model.Individual;
import model.Population;
import utility.Loader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GreedyAlgorithm {

    private List<City> cityList;
    private Functions functions = new Functions();


    public GreedyAlgorithm(List<City> cityList) {
        this.cityList = cityList;
    }

    public List<Double> resultsOfGreedyAlgorithm() {
        Population population = runGreedyAlgorithm();
        List<Double> fitnessess = new ArrayList<>();
        population.getPopulation().forEach(individual -> fitnessess.add(individual.getFitness()));
        Double best = functions.findBestFitness(fitnessess);
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

    public Population runGreedyAlgorithm() {

        Population population = new Population();
        List<City> cityList1 = cityList.stream().collect(Collectors.toList());
        List<Double> distances = new ArrayList<>();
        for (int i = 0; i < cityList.size(); i++) {
            Individual individual = findShortestWay(cityList1, i);
            population.addIndividual(individual);
            distances.add(functions.countFitness(individual));
            cityList1 = cityList.stream().collect(Collectors.toList());
        }
        //safeMeasurementsToFile(population);
        return population;
    }


    public double countDistance(City first_city, City second_city) {
        return Math.sqrt(
                Math.pow(first_city.getX() - second_city.getX(), 2)
                        + Math.pow(first_city.getY() - second_city.getY(), 2));
    }

    private City findClosestCity(List<City> cities, int position) {
        City city_to_compare = cities.get(position);
        int city_to_check_position = position;
        int counter = cities.size() - 1;
        int closest_city_position = 0;
        double shortest_distance = Double.POSITIVE_INFINITY;
        while ((counter > 0)) {
            if (city_to_check_position == cities.size() - 1) {
                city_to_check_position = 0;
            } else {
                city_to_check_position++;
            }
            double actual_distance = countDistance(city_to_compare, cities.get(city_to_check_position));
            if (actual_distance < shortest_distance) {
                shortest_distance = actual_distance;
                closest_city_position = city_to_check_position;
            }
            counter--;

        }
        return cities.get(closest_city_position);
    }


    private Individual findShortestWay(List<City> cities, int starting_position) {
        List<City> shortest_way = new ArrayList<>();
        shortest_way.add(cities.get(starting_position));
        int counter = cities.size() - 1;
        while (counter > 0) {
            City starting_city = cities.get(starting_position);
            City closest_city = findClosestCity(cities, starting_position);
            shortest_way.add(closest_city);
            cities.remove(starting_city);
            starting_position = cities.indexOf(closest_city);
            counter--;
        }
        return new Individual(shortest_way);
    }

    private void safeMeasurementsToFile(Population population) {
        Loader loader = new Loader();
        String data_to_save = "Fitness;\n";
        for (int i = 0; i < population.getPopulation().size(); i++) {
            data_to_save += population.getIndividual(i).getFitness().intValue() + ";\n";
        }
        loader.saveGreedyAlgorithmDataToFile(data_to_save);
    }
}
