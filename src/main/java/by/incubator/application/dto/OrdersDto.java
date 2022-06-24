package by.incubator.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersDto {
    private Long id;
    private Long vehicleId;
    private String defect;
    private Integer breakingAmount;
}
