package by.incubator.application.parsers.impl;

import by.incubator.application.engins.DieselEngine;
import by.incubator.application.engins.ElectricalEngine;
import by.incubator.application.engins.GasolineEngine;
import by.incubator.application.engins.Startable;
import by.incubator.application.mechanicService.TechnicalSpecialist;
import by.incubator.application.vehicle.Color;
import by.incubator.application.vehicle.Vehicle;
import by.incubator.application.entity.Rents;
import by.incubator.application.entity.Types;
import by.incubator.application.entity.Vehicles;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;
import by.incubator.application.infrastrucrure.core.annotations.InitMethod;
import by.incubator.application.parsers.ParserVehicle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParserVehicleFromFile implements ParserVehicle {
    @Autowired
    private TechnicalSpecialist technicalSpecialist;

    public ParserVehicleFromFile() {
    }

    @InitMethod
    public void init() {
    }

    @Override
    public List<Types> loadTypes(String inFile) {
        List<Types> newList = new ArrayList<>();
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

    @Override
    public List<Rents> loadRents(List<Vehicles> vehicles, String inFile) {
        List<Rents> newList = new ArrayList<>();
        File file = new File("src/main/resources/data/" + inFile + ".csv");
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                Scanner scanner = new Scanner(fileInputStream)
        ) {
            while (scanner.hasNext()) {
                String fileLine = scanner.nextLine();

                int from;
                int to;
                long id;
                Date date;
                DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                double price;

                from = fileLine.indexOf(',') + 1;
                to = fileLine.indexOf(',', from);

                id = Integer.parseInt(fileLine.substring(0, from - 1));
                date = new Date(formatter.parse(fileLine.substring(from, to)).getTime());
                price = Double.parseDouble(fileLine.substring(to + 1).replaceAll(",", ".").replaceAll("\"", " "));

                newList.add(new Rents(id, date, price));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newList;
    }

    @Override
    public List<Vehicles> loadVehicles(List<Types> vehicleTypes, String inFile) {
        List<Vehicles> newList = new ArrayList<>();
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

    private Types createType(String csvString) {
        int from;
        int to;
        long id;
        String name;
        double coefficient;

        from = csvString.indexOf(',') + 1;
        to = csvString.indexOf(',', from);

        id = Long.parseLong(csvString.substring(0, from - 1));

        name = csvString.substring(from, to);
        coefficient = Double.parseDouble(csvString.substring(to + 1).replaceAll(",", ".").replaceAll("\"", " "));

        return new Types(id, name, coefficient);
    }

    private Vehicles createVehicle(List<Types> vehicleTypes, String csvString) {
        String fileLine = csvString;
        int from;
        int to;
        long id;
        long vehicleType;
        String modelName;
        String registrationNumber;
        int weight;
        int manufactureYear;
        int mileage;
        String strColor;
        Color color = null;
        String strEngine;
        Startable engine = null;
        Integer volume = null;
        Integer consumption = null;
        String one;
        String two;
        String three = "";

        from = fileLine.indexOf(',');
        to = fileLine.indexOf(',', from + 1);

        id = Long.parseLong(fileLine.substring(0, from));
        vehicleType = Long.parseLong(fileLine.substring(from + 1, to));

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

            three = fileLine.substring(from + 1, to).replaceAll(",", ".");
        }

        for (Color c : Color.values()) {
            if (strColor.equals(c.toString())) {
                color = c;
                break;
            }
        }

        if (strEngine.equals("Electrical")) {
            volume = Integer.parseInt(one);
            consumption = Integer.parseInt(two);
        } else if (strEngine.equals("Diesel") || strEngine.equals("Gasoline")) {

            volume = (int) (Double.parseDouble(two) * 100);
            consumption = Integer.parseInt(three);
        } else {
            throw new RuntimeException("Unknown engine");
        }

        return new Vehicles(id, vehicleType, modelName, registrationNumber, weight,
                            manufactureYear, mileage, strColor, strEngine, volume, consumption);
    }
}
