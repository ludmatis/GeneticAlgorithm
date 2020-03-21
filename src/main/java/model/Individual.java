package model;

import algorithm.Functions;

import java.util.List;

public class Individual {

    private List<City> list_of_cities;
    private Double fitness;

    public Individual() {
    }

    public Individual(List<City> list_of_cities) {
        this.list_of_cities = list_of_cities;
        Functions functions = new Functions();
        fitness = functions.countFitness(this);
    }

    public List<City> getList_of_cities() {
        return list_of_cities;
    }

    public void setList_of_cities(List<City> list_of_cities) {
        this.list_of_cities = list_of_cities;
    }

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        final String[] to_return = {""};
        list_of_cities.forEach(city -> to_return[0] += city.getId() +  " ");
        return to_return[0] + "\n" + fitness;
    }


}
