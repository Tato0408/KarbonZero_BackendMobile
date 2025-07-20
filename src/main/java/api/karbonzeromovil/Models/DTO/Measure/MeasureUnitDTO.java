package api.karbonzeromovil.Models.DTO.Measure;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
@Getter @Setter
public class MeasureUnitDTO {
    @Getter @Setter
    private String idMeasureUnit;

    @NotBlank
    private String idMeasure;

    @NotBlank(message = "No es permitido dejar espacios en blanco")
    @Size(max=200)
    private String nameMeasureUnit;
}
