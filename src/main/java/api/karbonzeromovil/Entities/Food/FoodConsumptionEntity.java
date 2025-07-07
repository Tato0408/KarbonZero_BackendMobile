package api.karbonzeromovil.Entities.Food;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;


@Entity
@Table(name ="TBFOODCONSUMPTIONS")
@Getter @Setter
@ToString   @EqualsAndHashCode
public class FoodConsumptionEntity {
    @Id
    @GenericGenerator(name="db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDFOODCONSUMPTION")
    private String idFoodConsumption;

    @Column(name="IDUSER")
    private String idUser;

    @Column(name="CONSUMPTIONDATE")
    private LocalDate consumptionDate;

    @Column(name="LATITUDE")
    private Double latitude;

    @Column(name="LONGITUDE")
    private Double longitude;
}
