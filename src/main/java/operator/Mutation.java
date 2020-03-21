package operator;

import algorithm.Functions;
import model.City;
import model.Individual;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Mutation {

    private Double mutation_prob;
    Functions functions = new Functions();
    public Mutation(Double mutationProb) {
        this.mutation_prob = mutationProb;
    }

    public Individual swapMutation(Individual individual){
        Random random = new Random();
        List<City> cities = individual.getList_of_cities();
        int cities_amount = cities.size();
        int first_index = random.nextInt(cities_amount);
        int second_index = random.nextInt(cities_amount);
        while(second_index==first_index){
            second_index = random.nextInt(cities_amount);
        }
        Collections.swap(cities, first_index, second_index);
        individual.setList_of_cities(cities);
        individual.setFitness(functions.countFitness(individual));
        return individual;
    }

    public Individual inverseMutation(Individual individual){
        Random random = new Random();
        List<City> cities = individual.getList_of_cities();
        int cities_amount = cities.size();
        int first_index = random.nextInt(cities_amount);
        int second_index = random.nextInt(cities_amount);
        while(second_index==first_index){
            second_index = random.nextInt(cities_amount);
        }
        if(first_index>second_index){
            int temp = first_index;
            first_index = second_index;
            second_index = temp;
        }
        int j = second_index;
        for(int i = first_index; i < j; i++){
            Collections.swap(cities, i, j);
            j--;
        }
        individual.setList_of_cities(cities);
        individual.setFitness(functions.countFitness(individual));
        return individual;
    }
}
