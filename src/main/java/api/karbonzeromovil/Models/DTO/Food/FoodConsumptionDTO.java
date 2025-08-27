package api.karbonzeromovil.Models.DTO.Food;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString   @EqualsAndHashCode
@Getter @Setter
public class FoodConsumptionDTO {
    private String idFoodConsumption;

    @NotBlank()
    private String idUser;

    @NotNull
    private LocalDateTime consumptionDate;

    @Min(0)
    private Double latitude;

    @Min(0)
    private Double longitude;

    private String userName;
}
