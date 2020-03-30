//class for transforming loaded data from tsp file to City objects
package utility;

import model.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityCreator {

    private List<City> cityList = new ArrayList<>();

    public CityCreator() {
    }
    private String[] generateTspData() throws IOException {
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
        return tsp_data;
    }
    public List<City> generateCities() throws IOException {
        String[] read_from_file = generateTspData();
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
