package api.karbonzeromovil.Models.DTO.Transport;

import jakarta.validation.constraints.DecimalMax;
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
@Getter
@Setter
public class TransportConsumptionDTO {
    private String idTransportConsumption;
    @NotBlank
    private String idTransport;
    @NotBlank
    private String idUser;
    @NotNull @Positive @DecimalMax(value = "40075.017", inclusive = true)
    private Double distance;
    @NotNull
    private LocalDateTime consumptionDate;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
}
