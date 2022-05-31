package by.incubator.application.Main;

import by.incubator.application.Collections.*;
import by.incubator.application.MechanicService.*;
import by.incubator.application.infrastrucrure.core.impl.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = initInterfaceToImplementation();
        ApplicationContext context = new ApplicationContext("application", interfaceToImplementation);
        VehicleCollection vehicleCollection = context.getObject(VehicleCollection.class);
        Workroom workroom = context.getObject(Workroom.class);
        workroom.checkAllVehicles(vehicleCollection.getVehicles());
    }

    private static Map<Class<?>, Class<?>> initInterfaceToImplementation() {
        Map<Class<?>, Class<?>> map = new HashMap<>();
        map.put(Fixer.class, BadMechanicService.class);
        return map;
    }
}