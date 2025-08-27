package api.karbonzeromovil.Models.DTO.Service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter @Setter
public class MeasureUnitServiceDTO {
    private String idMeasureUnitService;
    @NotBlank
    private String idService;
    private String  serviceName;
    @NotBlank
    private String idMeasureUnit;
    private String measureUnitName;
    @Positive
    @NotNull
    private Double carbonFootprint;
}
