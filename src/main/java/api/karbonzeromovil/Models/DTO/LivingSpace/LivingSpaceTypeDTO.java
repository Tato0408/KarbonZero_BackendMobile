package api.karbonzeromovil.Models.DTO.LivingSpace;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString   @EqualsAndHashCode
@Getter @Setter
public class LivingSpaceTypeDTO {
    @Getter @Setter
    private String idLivingSpaceType;

    @NotBlank(message = "No es permitido dejar espacios en blanco")
    @Size(max=200)
    private String nameLivingSpaceType;

    @Min(0)
    @Positive
    private Double carbonFootPrint;

}
