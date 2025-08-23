package api.karbonzeromovil.Models.DTO.Car;

import api.karbonzeromovil.Entities.Car.CarEntity;
import api.karbonzeromovil.Entities.Car.FuelTypeEntity;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString   @EqualsAndHashCode
@Getter @Setter
public class TypeCarDTO {
    private String idTypeCar;

    @NotNull
    private String idCar;

    @NotNull
    private String idFuelType;
}
