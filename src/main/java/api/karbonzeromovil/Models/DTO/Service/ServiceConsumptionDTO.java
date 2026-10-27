package api.karbonzeromovil.Models.DTO.Service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@Getter @Setter
public class ServiceConsumptionDTO {
    private String idServiceConsumption;
    @NotBlank
    private String idMeasureUnitService;
    private String serviceName;
    private String measureUnitName;
    @NotBlank
    private String idUser;
    @NotNull @Positive
    private Double quantity;
    @NotNull
    private LocalDateTime consumptionDate;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
}
