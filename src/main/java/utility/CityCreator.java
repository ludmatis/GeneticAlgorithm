package utility;

import model.City;

import java.util.ArrayList;
import java.util.List;

public class CityCreator {

    private String[] read_from_file;
    private List<City> cityList = new ArrayList<>();

    public CityCreator(String[] list_of_cities) {
        this.read_from_file = list_of_cities;
    }

    public List<City> generateCities() {
        for (int i = 6; i < read_from_file.length; i++) {
            if (read_from_file[i] != null) {
                if (read_from_file[i].equals("EOF")) {
                    break;
                } else {
                    String[] line_from_file = read_from_file[i].split(" ");
                    int id = Integer.parseInt(line_from_file[0]);
                    double x = Double.parseDouble(line_from_file[1]);
                    double y = Double.parseDouble(line_from_file[2]);
                    cityList.add(new City(id, x, y));
                }
            }
        }
        return cityList;
    }
}
