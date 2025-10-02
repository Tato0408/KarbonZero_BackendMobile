package api.karbonzeromovil.Models.DTO.Car;

import api.karbonzeromovil.Entities.Car.TypeCarEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Check;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ToString   @EqualsAndHashCode
@Getter @Setter
@Check(constraints = "quantity > 0")
public class FuelConsumptionDTO {
    private String idFuelConsumption;

    @NotNull
    private String idTypeCar;
    private String typeFuel;
    private String typeSize;

    @Positive
    @NotNull
    private Double quantity;

    @NotNull
    private Date consumptionDate;

    private Double latitude;
    private Double longitude;
}
