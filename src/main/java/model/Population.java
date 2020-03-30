package model;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private List<Individual> population;

    public Population() {
        population = new ArrayList<>();
    }

    public Population(List<Individual> population) {
        this.population = population;
    }

    public Population(Population population){
        this.population = population.getPopulation();
    }
    public List<Individual> getPopulation() {
        return population;
    }

    public void setPopulation(List<Individual> population) {
        this.population = population;
    }

    public Individual getIndividual(Integer index){
        return population.get(index);
    }

    public void addIndividual(Individual individual){
        population.add(individual);
    }

    @Override
    public String toString() {
        final String[] to_return = {""};
        population.forEach(individual -> to_return[0] +=individual.toString() + "\n" );
        return to_return[0];
    }
}
