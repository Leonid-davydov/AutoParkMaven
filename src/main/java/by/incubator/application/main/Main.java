package by.incubator.application.main;

import by.incubator.application.checker.VehiclesChecker;
import by.incubator.application.collections.VehicleCollection;
import by.incubator.application.engins.Startable;
import by.incubator.application.entity.Rents;
import by.incubator.application.entity.Vehicles;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;
import by.incubator.application.mechanicService.Fixer;
import by.incubator.application.mechanicService.MechanicService;
import by.incubator.application.mechanicService.Workroom;
import by.incubator.application.infrastrucrure.core.impl.ApplicationContext;
import by.incubator.application.parsers.ParserBreakings;
import by.incubator.application.parsers.ParserVehicle;
import by.incubator.application.parsers.impl.ParserBreakingsFromDB;
import by.incubator.application.parsers.impl.ParserVehicleFromDB;
import by.incubator.application.parsers.impl.ParserVehicleFromFile;
import by.incubator.application.service.RentsService;
import by.incubator.application.service.TypesService;
import by.incubator.application.service.VehicleService;
import by.incubator.application.vehicle.Color;
import by.incubator.application.vehicle.Vehicle;
import by.incubator.application.vehicle.VehicleType;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Map<Class<?>, Class<?>> interfaceToImplementation = initInterfaceToImplementation();
        ApplicationContext context = new ApplicationContext("by.incubator.application", interfaceToImplementation);
        VehiclesChecker vehiclesChecker = context.getObject(VehiclesChecker.class);

        RentsService rentsService = context.getObject(RentsService.class);
        ParserVehicleFromFile parser = context.getObject(ParserVehicleFromFile.class);

        VehicleService vehicleService = context.getObject(VehicleService.class);
        VehicleCollection vehicleCollection = context.getObject(VehicleCollection.class);

        for (Rents rents : parser.loadRents(vehicleCollection.getVehicles(), "rents")){
            System.out.println(rents);
            rentsService.save(rents);
        }

    }

    public static Map<Class<?>, Class<?>> initInterfaceToImplementation() {
        Map<Class<?>, Class<?>> map = new HashMap<>();

        map.put(Fixer.class, MechanicService.class);
        map.put(ParserVehicle.class, ParserVehicleFromFile.class);
        map.put(ParserBreakings.class, ParserBreakingsFromDB.class);

        return map;
    }
}