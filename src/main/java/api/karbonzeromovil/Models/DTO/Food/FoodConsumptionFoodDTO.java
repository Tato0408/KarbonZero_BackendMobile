package api.karbonzeromovil.Models.DTO.Food;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString   @EqualsAndHashCode
@Getter @Setter
public class FoodConsumptionFoodDTO {
    @Getter @Setter
    private String idFoodConsumptionsFood;

    @NotBlank()
    private String idFood;

    @NotBlank
    private String idFoodConsumption;
}
