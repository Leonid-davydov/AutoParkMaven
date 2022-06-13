package by.incubator.application.parsers.impl;

import by.incubator.application.entity.Rents;
import by.incubator.application.entity.Types;
import by.incubator.application.entity.Vehicles;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;
import by.incubator.application.parsers.ParserVehicle;
import by.incubator.application.service.RentsService;
import by.incubator.application.service.TypesService;
import by.incubator.application.service.VehicleService;

import java.util.List;

public class ParserVehicleFromDB implements ParserVehicle {
    @Autowired
    private TypesService typesService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private RentsService rentsService;

    @Override
    public List<Types> loadTypes(String inFile) {
        return typesService.getAll();
    }

    @Override
    public List<Rents> loadRents(List<Vehicles> vehicles, String inFile) {
        return rentsService.getAll();
    }

    @Override
    public List<Vehicles> loadVehicles(List<Types> vehicleTypes, String inFile) {
        return vehicleService.getAll();
    }
}
