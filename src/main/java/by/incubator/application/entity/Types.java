package by.incubator.application.entity;

import by.incubator.application.infrastrucrure.orm.annotations.Column;
import by.incubator.application.infrastrucrure.orm.annotations.ID;
import by.incubator.application.infrastrucrure.orm.annotations.Table;
import lombok.*;

@Table(name = "types")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Types {
    @ID
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "coefTaxes")
    private Double coefTaxes;
}
