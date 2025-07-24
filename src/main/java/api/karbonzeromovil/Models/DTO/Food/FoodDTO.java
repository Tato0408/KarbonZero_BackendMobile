package api.karbonzeromovil.Models.DTO.Food;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
@Getter  @Setter
public class FoodDTO {
    private String idFood;

    @NotBlank
    private String idFoodCategory;

    @NotBlank(message = "No se permiten espacios en blanco")
    @Size(max=200)
    private String foodName;
}
