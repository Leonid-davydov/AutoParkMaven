package Utils;

import Collections.VehicleCollection;
import Service.MechanicService;
import Vehicle.Vehicle;

public class Mechanic {
    public static void detectAndRepairAll(VehicleCollection vehicleCollection, MechanicService mechanicService) {
        for (Vehicle v : vehicleCollection.getVCollection()) {
            if (mechanicService.detectAndRepair(v))
                System.out.println(v);
        }
    }

    public static void detectBreakingAll(VehicleCollection vehicleCollection, MechanicService mechanicService) {
        for (Vehicle v : vehicleCollection.getVCollection()) {
            mechanicService.detectBreaking(v);
        }
    }

    public static Vehicle findMost(VehicleCollection vehicleCollection, MechanicService mechanicService) {
        int max = mechanicService.findMost();

        for (Vehicle v : vehicleCollection.getVCollection()) {
            if (v.getId() == max) {
                return v;
            }
        }
        System.out.println("Error: The vehicle is not found");
        return null;
    }

}
