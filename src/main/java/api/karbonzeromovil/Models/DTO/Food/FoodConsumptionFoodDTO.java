package api.karbonzeromovil.Models.DTO.Food;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString   @EqualsAndHashCode
@Getter @Setter
public class FoodConsumptionFoodDTO {
    @Getter @Setter
    private String idFoodConsumptionsFood;

    private String idFood;

    private String idFoodConsumption;
}
