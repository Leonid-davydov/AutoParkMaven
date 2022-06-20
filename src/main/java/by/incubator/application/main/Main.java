package by.incubator.application.main;

import by.incubator.application.collections.VehicleCollection;
import by.incubator.application.entity.Orders;
import by.incubator.application.entity.Vehicles;
import by.incubator.application.infrastrucrure.core.impl.ApplicationContext;
import by.incubator.application.mechanicService.Fixer;
import by.incubator.application.mechanicService.MechanicService;
import by.incubator.application.parsers.ParserBreakings;
import by.incubator.application.parsers.ParserVehicle;
import by.incubator.application.parsers.impl.ParserBreakingsFromDB;
import by.incubator.application.parsers.impl.ParserVehicleFromDB;
import by.incubator.application.service.OrdersService;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = initInterfaceToImplementation();
        ApplicationContext context = new ApplicationContext("by.incubator.application", interfaceToImplementation);
        VehicleCollection vehicleCollection = context.getObject(VehicleCollection.class);
        MechanicService mechanicService = context.getObject(MechanicService.class);
        OrdersService ordersService = context.getObject(OrdersService.class);

        for (Vehicles vehicles : vehicleCollection.getVehicles()){
            System.out.println(vehicles);
            mechanicService.detectBreaking(vehicles);
        }

        for (Orders orders : ordersService.getAll()){
            System.out.println(orders);

        }
    }

    public static Map<Class<?>, Class<?>> initInterfaceToImplementation() {
        Map<Class<?>, Class<?>> map = new HashMap<>();

        map.put(Fixer.class, MechanicService.class);
        map.put(ParserVehicle.class, ParserVehicleFromDB.class);
        map.put(ParserBreakings.class, ParserBreakingsFromDB.class);

        return map;
    }
}