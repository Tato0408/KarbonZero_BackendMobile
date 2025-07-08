package api.karbonzeromovil.Entities.Food;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TBFOODCONSUMPTIONSFOOD")
@Getter @Setter
@ToString   @EqualsAndHashCode
public class FoodConsumptionFoodEntity {
    @Id
    @GenericGenerator(name="db-uuid", strategy = "guid")
    @GeneratedValue(generator="db-uuid")
    @Column(name="IDFOODCONSUMPTIONSFOOD")
    private String idFoodConsumptionsFood;

    @Column(name="IDFOOD")
    private String idFood;

    @Column(name="IDFOODCONSUMPTION")
    private String idFoodConsumption;
}
