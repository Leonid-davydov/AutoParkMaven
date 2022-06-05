package by.incubator.application.Main;

import by.incubator.application.Collections.*;
import by.incubator.application.MechanicService.*;
import by.incubator.application.infrastrucrure.configurators.ObjectConfigurator;
import by.incubator.application.infrastrucrure.configurators.impl.PropertyObjectConfigurator;
import by.incubator.application.infrastrucrure.core.impl.ApplicationContext;
import by.incubator.application.infrastrucrure.orm.ConnectionFactory;
import by.incubator.application.infrastrucrure.orm.EntityManager;
import by.incubator.application.infrastrucrure.orm.impl.ConnectionFactoryImpl;
import by.incubator.application.infrastrucrure.orm.impl.EntityManagerImpl;
import by.incubator.application.parsers.ParserVehicle;
import by.incubator.application.parsers.impl.ParserVehicleFromDB;
import by.incubator.application.parsers.impl.ParserVehicleFromFile;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = initInterfaceToImplementation();
        ApplicationContext context = new ApplicationContext("application", interfaceToImplementation);
        VehicleCollection vehicleCollection = context.getObject(VehicleCollection.class);
        System.out.println(vehicleCollection.getVehicles());
//        Workroom workroom = context.getObject(Workroom.class);
//        workroom.checkAllVehicles(vehicleCollection.getVehicles());
    }

    private static Map<Class<?>, Class<?>> initInterfaceToImplementation() {
        Map<Class<?>, Class<?>> map = new HashMap<>();

        map.put(Fixer.class, MechanicService.class);
        map.put(ParserVehicle.class, ParserVehicleFromDB.class);
        map.put(EntityManager.class, EntityManagerImpl.class);
        map.put(ConnectionFactory.class, ConnectionFactoryImpl.class);

        return map;
    }
}