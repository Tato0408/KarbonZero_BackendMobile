package api.karbonzeromovil.Models.DTO.Food;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @Size(max=200)
    private String foodCategoryName;

    @Min(0)
    @Positive
    private double carbonFootPrint;
}
