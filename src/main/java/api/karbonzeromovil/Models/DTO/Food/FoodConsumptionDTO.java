package api.karbonzeromovil.Models.DTO.Food;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@ToString   @EqualsAndHashCode
@Getter @Setter
public class FoodConsumptionDTO {
    @Getter @Setter
    private String idFoodConsumption;

    @NotBlank()
    private String idUser;

    @NotBlank
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate consumptionDate;

    @NotBlank
    @Min(0)
    private Double latitude;

    @NotBlank
    @Min(0)
    private Double longitude;
}
