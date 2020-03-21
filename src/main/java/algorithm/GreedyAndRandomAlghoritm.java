package algorithm;

import model.City;
import model.Individual;
import model.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GreedyAndRandomAlghoritm {

    private static final Integer RANDOM_ALGORITHM_RUNS = 10000;
    private Random random = new Random();
    private List<City> cityList;
    private List<Double> one_way_distances = new ArrayList<>();
    private List<List<Double>> all_distances = new ArrayList<>();
    private List<City> shortest_way = new ArrayList<>();
    Functions functions = new Functions();


    public GreedyAndRandomAlghoritm(List<City> cityList) {
        this.cityList = cityList;
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
        //System.out.println(population);
        //distances.forEach(System.out::println);

        return population;
    }

//    public void runRandomAlgorithm() {
//
//        List<List<City>> city_ways = new ArrayList<>();
//        List<City> city_list_duplicate = cityList.stream().collect(Collectors.toList());
//        List<Double> distances = new ArrayList<>();
//        for (int i = 0; i < RANDOM_ALGORITHM_RUNS; i++) {
//            List<City> random_list = getRandomWay(city_list_duplicate);
//            distances.add(countWholeWay(random_list));
//            city_ways.add(random_list);
//            city_list_duplicate = cityList.stream().collect(Collectors.toList());
//        }

        //printCities(city_ways);
        //distances.forEach(System.out::println);
//        System.out.println(findShortestWay(distances));
//    }

    public double countDistance(City first_city, City second_city) {
        return Math.sqrt(
                Math.pow(first_city.getX() - second_city.getX(), 2)
                        + Math.pow(first_city.getY() - second_city.getY(), 2));
    }

    private Double findShortestWay(List<Double> list){
        final Double[] shortest_way = {Double.POSITIVE_INFINITY};
        list.forEach(distance -> {
            if(distance< shortest_way[0]){
                shortest_way[0] = distance;
            }
        });
        return shortest_way[0];
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
        one_way_distances.add(shortest_distance);
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

    private List<City> getRandomWay(List<City> cities) {
        int counter = cityList.size();
        List<City> random_city_list = new ArrayList<>();
        while (counter > 0) {
            int random_index = random.nextInt(cities.size());
            random_city_list.add(cities.get(random_index));
            cities.remove(random_index);
            counter--;
        }
        return random_city_list;
    }

    private Double countWholeWay(List<City> cities) {
        Double distance = 0.0;
        for (int i = 1; i < cities.size(); i++) {
            City first_city = cities.get(i - 1);
            City second_city = cities.get(i);
            distance += countDistance(first_city, second_city);
        }
        int last_city_position = cities.get(cities.size() - 1).getId() - 1;
        Double last_distance = countDistance(cities.get(0), cityList.get(last_city_position));
        distance+=last_distance;
        return distance;
    }


    private void printCities(List<List<City>> cities) {
        cities.forEach(citiess -> {
            citiess.forEach(city -> {
                System.out.print(city.getId() + " ");
            });
            System.out.println();
        });
    }

}
