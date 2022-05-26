package by.incubator.application.Vehicle;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import by.incubator.application.infrastrucrure.core.annotations.InitMethod;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserBreakingsFromFile {
    public ParserBreakingsFromFile() {}

    @InitMethod
    public void init() {}

    private static String[] details = new String[] {"Filter", "Sleeve", "Shaft", "Axis", "Candle", "Oil", "GRM", "ShRUS"};

    private static String PATH = "src/main/resources/data/orders.csv";
    private static String PATH_COPY = "src/main/resources/data/ordersCopy.csv";

    public Map<String, Integer> detectBreaking(Vehicle vehicle) {

        Map<String, Integer> breakdowns = new HashMap<>();
        int id = vehicle.getId();
        String line = "" + id;
        String detail;
        Integer number;

        for (int i = 0; i < 3; i++){
            detail = details[getRandomInteger(0,7)];
            number = getRandomInteger(0,2);

            if (number != 0) {
                breakdowns.put(detail, number);
            }
        }

        for(Map.Entry<String, Integer> entry : breakdowns.entrySet()){
            line += "," + entry.getKey() + "," + entry.getValue();
        }

        if (!breakdowns.isEmpty())
            write(line);

        return breakdowns;
    }

    public void repair(Vehicle vehicle) {
        String currentLine;
        String vehicleId = Integer.toString(vehicle.getId());

        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_COPY));
             BufferedWriter writer = new BufferedWriter(new FileWriter(PATH))){

            while ((currentLine = reader.readLine()) != null && !currentLine.isEmpty()) {
                if (vehicleId.equals(getId(currentLine))) {
                    continue;
                } else {
                    writer.write(currentLine + "\n");
                }
            }

        } catch (IOException e) {
            System.out.println("File reading exception");
        }

        copyOrdersFile();
    }

    public boolean isBroken(Vehicle vehicle) {
        List<String[]> lines;

        try (CSVReader reader = new CSVReader(new FileReader(PATH))) {

            lines = reader.readAll();

            for (int i = 1; i < lines.size(); i++) {
                if (Integer.parseInt(lines.get(i)[0].trim()) == vehicle.getId())
                    return true;
            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return false;
    }

    private int getRandomInteger(int min, int max) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

    private void write(String str) {
        try {
            str += "\n";
            Files.write(Paths.get(PATH),str.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        copyOrdersFile();
    }

    public int findMost() {
        int id = 0;
        int maxBreakdowns = 0;
        int sum;
        List<String[]> lines;

        try (CSVReader reader = new CSVReader(new FileReader(PATH))) {

            lines = reader.readAll();

            for (int i = 1; i < lines.size(); i++) {
                sum = 0;

                for (int j = 2; j < lines.get(i).length; j +=2){
                    sum += Integer.parseInt(lines.get(i)[j].trim());
                }

                if (sum > maxBreakdowns) {
                    maxBreakdowns = sum;
                    id = Integer.parseInt(lines.get(i)[0].trim());
                }
            }

            System.out.println("Max number of Breakdowns is " + maxBreakdowns);

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return id;
    }

    private String getId(String breakings) {
        String id = breakings.substring(0, breakings.indexOf(','));
        return id;
    }

    private void copyOrdersFile() {
        String currentLine;
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_COPY))){
            while ((currentLine = reader.readLine()) != null && !currentLine.isEmpty()) {
                writer.write(currentLine + "\n");
            }
        } catch (IOException e) {
            System.out.println("File reading exception");
        }
    }
}
