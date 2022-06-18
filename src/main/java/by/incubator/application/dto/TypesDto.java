package by.incubator.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TypesDto {
    private long id;
    private String name;
    private double coefTaxes;
}
