package api.karbonzeromovil.Models.DTO.Food;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
@Getter @Setter
public class FoodCategoryDTO {
    @Getter @Setter
    private String idFoodCategory;
    @NotBlank(message="No se permiten espacios en blanco")
    private String foodCategoryName;
    private double carbonFootPrint;
}
