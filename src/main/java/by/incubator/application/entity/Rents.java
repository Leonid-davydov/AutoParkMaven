package by.incubator.application.entity;

import by.incubator.application.infrastrucrure.orm.annotations.Column;
import by.incubator.application.infrastrucrure.orm.annotations.ID;
import by.incubator.application.infrastrucrure.orm.annotations.Table;
import lombok.*;

import java.sql.Date;

@Table(name = "rents")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rents {
    @ID(name = "Id")
    private Long Id;
    @Column(name = "carId")
    private Long carId;
    @Column(name = "date")
    private Date date;
    @Column(name = "cost")
    private Double cost;
}
