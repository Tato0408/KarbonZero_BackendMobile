package api.karbonzeromovil.Models.DTO.Food;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString   @EqualsAndHashCode
@Getter @Setter
public class FoodConsumptionDTO {
    @Getter @Setter
    private String idFoodConsumption;
    private String idUser;
    private LocalDate consumptionDate;
    private Double latitude;
    private Double longitude;
}
