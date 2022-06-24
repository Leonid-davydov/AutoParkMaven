package by.incubator.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Date;

@Getter
@Builder
public class RentsDto {
    private Long id;
    private Long carId;
    private Date date;
    private Double cost;
}
