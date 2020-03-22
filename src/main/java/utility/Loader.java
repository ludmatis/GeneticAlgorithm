package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Loader {

    private static final String BERLIN_11_MODIFIED_TSP = "berlin11_modified.tsp";
    private static final String BERLIN_52 = "berlin52.tsp";
    private static final String KROA100 = "kroA100.tsp";
    private static final String GR666 = "gr666.tsp";
    private static final String FL417 = "fl417.tsp";

    private FileReader fileReader;
    private BufferedReader bufferedReader;



    public void init() {
        File file = new File(FL417);
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bufferedReader = new BufferedReader(fileReader);
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }
}
