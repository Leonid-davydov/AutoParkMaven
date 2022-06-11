package by.incubator.application.main;

import by.incubator.application.checker.VehiclesChecker;
import by.incubator.application.collections.VehicleCollection;
import by.incubator.application.mechanicService.Fixer;
import by.incubator.application.mechanicService.MechanicService;
import by.incubator.application.mechanicService.Workroom;
import by.incubator.application.infrastrucrure.core.impl.ApplicationContext;
import by.incubator.application.parsers.ParserBreakings;
import by.incubator.application.parsers.ParserVehicle;
import by.incubator.application.parsers.impl.ParserBreakingsFromDB;
import by.incubator.application.parsers.impl.ParserVehicleFromDB;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Map<Class<?>, Class<?>> interfaceToImplementation = initInterfaceToImplementation();
        ApplicationContext context = new ApplicationContext("by.incubator.application", interfaceToImplementation);
        VehiclesChecker vehiclesChecker = context.getObject(VehiclesChecker.class);

        vehiclesChecker.vehiclesFromDbToWorkroom(context);

        Thread.sleep(120000);
    }

    private static Map<Class<?>, Class<?>> initInterfaceToImplementation() {
        Map<Class<?>, Class<?>> map = new HashMap<>();

        map.put(Fixer.class, MechanicService.class);
        map.put(ParserVehicle.class, ParserVehicleFromDB.class);
        map.put(ParserBreakings.class, ParserBreakingsFromDB.class);

        return map;
    }
}