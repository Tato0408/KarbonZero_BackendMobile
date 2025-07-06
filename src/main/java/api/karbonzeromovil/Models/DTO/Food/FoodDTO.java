package api.karbonzeromovil.Models.DTO.Food;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
@Getter  @Setter
public class FoodDTO {
    @Getter @Setter
    private String idFood;
    @Getter @Setter
    private String idFoodCategory;
    @NotBlank(message = "No se permiten espacios en blanco")
    private String foodName;
}
