//class for performing crossing operations
package operator;

import model.City;
import model.Individual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Crossover {

    public Crossover() {

    }

    public List<Individual> partiallyMatchedCrossover(Individual parent1, Individual parent2) {
        Random random = new Random();
        List<City> parent1_cities = parent1.getList_of_cities();
        List<City> parent2_cities = parent2.getList_of_cities();

        int first_cut_index = random.nextInt(parent1_cities.size());
        int second_cut_index = random.nextInt(parent2_cities.size() + 1);

        while (first_cut_index == second_cut_index) {
            second_cut_index = random.nextInt(parent1_cities.size() + 1);
        }

        if (first_cut_index > second_cut_index) {
            int temp = first_cut_index;
            first_cut_index = second_cut_index;
            second_cut_index = temp;
        }

        Individual child1 = generateChildFromPMX(parent1_cities, parent2_cities, first_cut_index, second_cut_index);
        Individual child2 = generateChildFromPMX(parent2_cities, parent1_cities, first_cut_index, second_cut_index);

        List<Individual> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);
        return children;


    }

    private Individual generateChildFromPMX(List<City> parent1_city_list, List<City> parent2_city_list,
                                            int first_cut_point, int second_cut_point) {
        City[] child = new City[parent1_city_list.size()];
        for (int i = 0; i < child.length; i++) {
            child[i] = null;
        }
        List<City> cut_subsequence = parent1_city_list.subList(first_cut_point, second_cut_point);
        //cities from P2 that are not in subsequence from P1
        List<City> parent2_only = new ArrayList<>();
        //cities from Child that are at the same index as cities above
        List<City> match_for_parent2_only = new ArrayList<>();
        int temp_index = 0;
        //filling child with subsequence from P1, left place is null
        for (int i = first_cut_point; i < second_cut_point; i++) {
            child[i] = cut_subsequence.get(temp_index);
            temp_index++;
        }

        //filling parent2_only with unique cities
        for (int i = first_cut_point; i < second_cut_point; i++) {
            boolean exists_in_second_table = false;
            for (int j = first_cut_point; j < second_cut_point; j++) {
                if (parent1_city_list.get(j).getId() == parent2_city_list.get(i).getId()) {
                    exists_in_second_table = true;
                }
            }
            if (!exists_in_second_table) {
                parent2_only.add(parent2_city_list.get(i));
            }
        }
        //filing match_for_parent2_only
        for (int i = 0; i < parent2_only.size(); i++) {
            int index = parent2_city_list.indexOf(parent2_only.get(i));
            match_for_parent2_only.add(parent1_city_list.get(index));
        }
        //filling child with cities from parent2_only at indexes where cities from match_for_parent2_only are in parent2
        // with taking to consideration taken place
        for (int i = 0; i < parent2_only.size(); i++) {
            int index = parent2_city_list.indexOf(match_for_parent2_only.get(i));
            while(child[index]!=null){
                index = parent2_city_list.indexOf(child[index]);
            }
            child[index] = parent2_only.get(i);
        }
        //filling child with left cities from parent2
        for (int i = 0; i < child.length; i++) {
            if (child[i] == null) {
                child[i] = parent2_city_list.get(i);
            }
        }
        List<City> to_return = new ArrayList<>(Arrays.asList(child));
        return new Individual(to_return);

    }

}

