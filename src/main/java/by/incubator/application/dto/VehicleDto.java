package by.incubator.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VehicleDto {
    public long typeId;
    private String typeName;
    private double taxCoefficient;
    private String modelName;
    private int manufactureYear;
    private String registrationNumber;
    private double weight;
    private int mileage;
    private String color;
    private  double tankVolume;
    private String engineName;
    private double engineTaxCoefficient;
    private long id;
    private double per100km;
    private double maxKm;
    private double tax;
    private double income;
}
