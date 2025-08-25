package api.karbonzeromovil.Models.DTO.Car;

import api.karbonzeromovil.Entities.Car.CarEntity;
import api.karbonzeromovil.Entities.Car.CarSizeEntity;
import api.karbonzeromovil.Entities.User.UserEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString   @EqualsAndHashCode
@Getter @Setter
public class CarDTO {
    private String idCar;

    @NotNull
    private String idUser;
    private  String username;
    @NotNull
    private String idCarSize;
    private String sizeC02;
}
