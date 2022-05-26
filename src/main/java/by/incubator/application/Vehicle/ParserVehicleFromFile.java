package by.incubator.application.Vehicle;

import by.incubator.application.Engins.DieselEngine;
import by.incubator.application.Engins.ElectricalEngine;
import by.incubator.application.Engins.GasolineEngine;
import by.incubator.application.Engins.Startable;
import by.incubator.application.Rent.Rent;
import by.incubator.application.Service.TechnicalSpecialist;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;
import by.incubator.application.infrastrucrure.core.annotations.InitMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParserVehicleFromFile {
    @Autowired
    private TechnicalSpecialist technicalSpecialist;

    public ParserVehicleFromFile() {}

    @InitMethod
    public void init() {
    }

    public List<VehicleType> loadTypes(String inFile) {
        List<VehicleType> newList = new ArrayList<>();
        File file = new File("src/main/resources/data/" + inFile + ".csv");
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                Scanner scanner = new Scanner(fileInputStream)
        ) {
            while (scanner.hasNext()) {
                String fileLine = scanner.nextLine();
                newList.add(createType(fileLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newList;
    }

    List<Rent> loadRents(List<Vehicle> vehicles, String inFile) {
        List<Rent> newList = new ArrayList<>();
        File file = new File("src/main/resources/data/" + inFile + ".csv");
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                Scanner scanner = new Scanner(fileInputStream)
        ) {
            while (scanner.hasNext()) {
                String fileLine = scanner.nextLine();

                int from;
                int to;
                int id;
                Date date;
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                double price;

                from = fileLine.indexOf(',') + 1;
                to = fileLine.indexOf(',', from);

                id = Integer.parseInt(fileLine.substring(0, from - 1));
                date = format.parse(fileLine.substring(from, to));
                price = Double.parseDouble(fileLine.substring(to + 1).replaceAll(",", ".").replaceAll("\"", " "));

                newList.add(new Rent(id, date, price));
                for (Vehicle v : vehicles) {
                    if (v.getId() == id) {
                        v.getHistory().add(new Rent(id, date, price));
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newList;
    }

    public List<Vehicle> loadVehicles(List<VehicleType> vehicleTypes, String inFile) {
        List<Vehicle> newList = new ArrayList<>();
        File file = new File("src/main/resources/data/" + inFile + ".csv");
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                Scanner scanner = new Scanner(fileInputStream)
        ) {
            while (scanner.hasNext()) {
                String fileLine = scanner.nextLine();
                newList.add(createVehicle(vehicleTypes, fileLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newList;
    }

    VehicleType createType(String csvString) {
        int from;
        int to;
        int id;
        String name;
        double coefficient;

        from = csvString.indexOf(',') + 1;
        to = csvString.indexOf(',', from);

        id = Integer.parseInt(csvString.substring(0, from - 1));

        name = csvString.substring(from, to);
        coefficient = Double.parseDouble(csvString.substring(to + 1).replaceAll(",", ".").replaceAll("\"", " "));

        return new VehicleType(id, name, coefficient);
    }

    Vehicle createVehicle(List<VehicleType> vehicleTypes, String csvString) {
        String fileLine = csvString;
        int from;
        int to;
        int id;
        int vehicleType;
        VehicleType type;
        String modelName;
        String registrationNumber;
        int weight;
        int manufactureYear;
        int mileage;
        String strColor;
        Color color = null;
        String strEngine;
        Startable engine = null;
        String one;
        String two;
        String three = "";

        from = fileLine.indexOf(',');
        to = fileLine.indexOf(',', from + 1);

        id = Integer.parseInt(fileLine.substring(0, from));
        vehicleType = Integer.parseInt(fileLine.substring(from + 1, to));

        from = to;
        to = fileLine.indexOf(',', from + 1);

        modelName = fileLine.substring(from + 1, to);

        from = to;
        to = fileLine.indexOf(',', from + 1);

        registrationNumber = fileLine.substring(from + 1, to);

        from = to;
        to = fileLine.indexOf(',', from + 1);

        weight = Integer.parseInt(fileLine.substring(from + 1, to));

        from = to;
        to = fileLine.indexOf(',', from + 1);

        manufactureYear = Integer.parseInt(fileLine.substring(from + 1, to));

        from = to;
        to = fileLine.indexOf(',', from + 1);

        mileage = Integer.parseInt(fileLine.substring(from + 1, to));

        from = to;
        to = fileLine.indexOf(',', from + 1);

        strColor = fileLine.substring(from + 1, to);

        from = to;
        to = fileLine.indexOf(',', from + 1);

        strEngine = fileLine.substring(from + 1, to);

        if (fileLine.charAt(to + 1) == '"') {
            from = to + 2;
            to = fileLine.indexOf('"', from + 1);
        } else {
            from = to + 1;
            to = fileLine.indexOf(',', from + 1);
        }

        one = fileLine.substring(from, to).replaceAll(",", ".");

        if (fileLine.charAt(to + 1) == '"') {
            from = to + 2;
            to = fileLine.indexOf('"', from + 1);
        } else if (fileLine.charAt(to + 2) == '"') {
            from = to + 3;
            to = fileLine.indexOf('"', from + 1);
        } else {
            from = to + 1;
            if (strEngine.equals("Electrical"))
                to = fileLine.length();
            else
                to = fileLine.indexOf(',', from + 1);
        }

        two = fileLine.substring(from, to).replaceAll(",", ".");

        if (!strEngine.equals("Electrical")) {
            if (fileLine.charAt(to + 2) == '"') {
                from = to + 3;
                to = fileLine.indexOf('"', from + 1);
            } else {
                from = to + 1;
                to = fileLine.length();
            }

            three = fileLine.substring(from, to).replaceAll(",", ".");
        }

        type = vehicleTypes.get(vehicleType - 1);
        for (Color c : Color.values()) {
            if (strColor.equals(c.toString())) {
                color = c;
                break;
            }
        }

        if (strEngine.equals("Electrical")) {
            engine = new ElectricalEngine(Integer.parseInt(one), Integer.parseInt(two));
        } else if (strEngine.equals("Diesel")) {
            engine = new DieselEngine(Double.parseDouble(one), Double.parseDouble(two), Double.parseDouble(three));
        } else if (strEngine.equals("Gasoline")) {
            engine = new GasolineEngine(Double.parseDouble(one), Double.parseDouble(two), Double.parseDouble(three));
        } else {
        }

        return new Vehicle(id, type, modelName, registrationNumber, weight,
                manufactureYear, mileage, color, engine);
    }

    public void insert(List<Vehicle> vehicles, int index, Vehicle v) {
        if (index >= 0 && index < vehicles.size())
            vehicles.set(index, v);
        else
            vehicles.add(v);
    }

    public int delete(List<Vehicle> vehicles, int index) {
        if (index >= 0 && index < vehicles.size()) {
            vehicles.remove(index);
            return index;
        } else
            return -1;
    }

    public double sumTotalProfit(List<Vehicle> vehicles) {
        double sum = 0.0;
        for (Vehicle v : vehicles) {
            sum += v.getTotalProfit();
        }
        return sum;
    }

    public void display(List<Vehicle> vehicles) {
        System.out.println(String.format("%3s %10s %20s %12s %15s %7s %10s %10s %10s %10s %10s",
                "Id", "Type", "ModelName", "Number", "Weight (kg)", "Year", "Millage", "by.incubator.application.Vehicle.Color", "Income", "Tax", "Profit"));

        for (Vehicle v : vehicles) {
            System.out.println(String.format("%3d %10s %20s %12s %15d %7d %10d %10s %10s %10s %10s",
                    v.getId(),
                    v.getVehicleType().getName(),
                    v.getModelName(),
                    v.getRegistrationNumber(),
                    v.getWeight(),
                    v.getManufactureYear(),
                    v.getMileage(),
                    v.getColor(),
                    String.format("%.2f", v.getTotalIncome()),
                    String.format("%.2f", v.getCalcTaxPerMonth()),
                    String.format("%.2f", v.getTotalProfit())
            ));
        }
        System.out.println(String.format("Total %121s", String.format("%.2f", sumTotalProfit(vehicles))));
    }

    public void sort(List<Vehicle> vehicles, Comparator<Vehicle> comparator) {
        vehicles.sort(comparator);
    }
}
