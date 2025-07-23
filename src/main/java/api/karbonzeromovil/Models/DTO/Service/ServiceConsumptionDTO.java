package api.karbonzeromovil.Models.DTO.Service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@Getter @Setter
public class ServiceConsumptionDTO {
    private String idServiceConsumption;
    @NotBlank
    private String idMeasureUnitService;
    @NotBlank
    private String idUser;
    @NotNull @Positive
    private Number quantity;
    @NotNull
    private LocalDateTime consumptionDate;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
}
