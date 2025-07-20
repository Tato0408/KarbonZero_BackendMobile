package api.karbonzeromovil.Models.DTO.Car;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString   @EqualsAndHashCode
@Getter @Setter
public class FuelTypeDTO {

    private String idFuelType;

    @NotNull
    private String idMeasureUnit;

    @NotBlank @Size (max = 200)
    private String name;

    @Positive
    @NotNull
    private Double carbonFootprint;
}
