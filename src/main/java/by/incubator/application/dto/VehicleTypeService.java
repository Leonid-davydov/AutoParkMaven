package by.incubator.application.dto;

import by.incubator.application.entity.Rents;
import by.incubator.application.entity.Types;
import by.incubator.application.infrastrucrure.core.annotations.Autowired;
import by.incubator.application.service.RentsService;
import by.incubator.application.service.TypesService;
import by.incubator.application.service.VehicleService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleTypeService {
    @Autowired
    VehicleService vehicleService;
    @Autowired
    TypesService typesService;
    @Autowired
    RentsService rentsService;
    @Autowired
    Logic logic;

    public List<VehicleDto> getVehicles() {
        return vehicleService.getAll().stream().map(vehicles -> {
            Types types = typesService.get(vehicles.getTypesId());

            return VehicleDto.builder()
                    .id(vehicles.getId())
                    .typeId(vehicles.getTypesId())
                    .typeName(types.getName())
                    .taxCoefficient(typesService.get(vehicles.getTypesId()).getCoefTaxes())
                    .color(vehicles.getColor())
                    .engineName(vehicles.getEngineType())
                    .engineTaxCoefficient(logic.getEngineTaxCoefficient(vehicles))
                    .tax(logic.getTax(vehicles, types))
                    .manufactureYear(vehicles.getManufactureYear())
                    .mileage(vehicles.getMileage())
                    .modelName(vehicles.getModel())
                    .registrationNumber(vehicles.getRegistrationNumber())
                    .tankVolume(vehicles.getVolume())
                    .weight(vehicles.getWeight())
                    .per100km(vehicles.getConsumption())
                    .maxKm(logic.getMaxKm(vehicles))
                    .income(logic.getIncome(vehicles))
                    .build();
        }).collect(Collectors.toList());
    }

    public List<VehicleTypeDto> getTypes() {
        return typesService.getAll().stream().map(types -> {

            return VehicleTypeDto.builder()
                    .id(types.getId())
                    .name(types.getName())
                    .taxCoefficient(types.getCoefTaxes())
                    .build();
        }).collect(Collectors.toList());
    }

    public List<RentsDto> getRent(int id) {
        List<RentsDto> rentsDtoList = new ArrayList<>();

        for (Rents rents : rentsService.getAll()) {
            if (rents.getCarId() == id) {
                rentsDtoList.add(RentsDto.builder()
                        .id(rents.getId())
                        .carId(rents.getCarId())
                        .date(rents.getDate())
                        .cost(rents.getCost())
                        .build());
            }
        }

        return rentsDtoList;
    }
}
