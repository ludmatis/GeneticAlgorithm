//class for loading and saving data to and from files
package utility;

import java.io.*;

public class Loader {

    private static final String BERLIN_11_MODIFIED_TSP = "berlin11_modified.tsp";
    private static final String BERLIN_52 = "berlin52.tsp";
    private static final String KROA100 = "kroA100.tsp";
    private static final String KROA200 = "kroA200.tsp";
    private static final String KROA150 = "kroA150.tsp";
    private static final String GR666 = "gr666.tsp";
    private static final String FL417 = "fl417.tsp";
    private static final String CSVFILE = "dane.csv";
    private static final String RANDOM = "random.csv";
    private static final String GREEDY = "greedy.csv";
    private FileReader fileReader;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String stringBuffer = "";



    public void init() {
        File file = new File(FL417);
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bufferedReader = new BufferedReader(fileReader);

    }
    private  void initForCSVfileTOSAVE(){
        File file = new File(CSVFILE);
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedWriter = new BufferedWriter(fileWriter);
    }
    private  void initForRandomAlgToSAVE(){
        File file = new File(RANDOM);
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedWriter = new BufferedWriter(fileWriter);
    }
    private  void initForGreedyAlgToSAVE(){
        File file = new File(GREEDY);
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedWriter = new BufferedWriter(fileWriter);
    }
    public void saveGenerationDataToFile(String data){
        initForCSVfileTOSAVE();
        try {
            bufferedWriter.write(data);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveRandomAlgorithmDataToFIle(String data){
        initForRandomAlgToSAVE();
        try {
            bufferedWriter.write(data);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveGreedyAlgorithmDataToFile(String data){
        initForGreedyAlgToSAVE();
        try {
            bufferedWriter.write(data);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }
}
