package api.karbonzeromovil.Models.DTO.LivingSpace;


import jakarta.validation.constraints.NotBlank;
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
    private String nameLivingSpaceType;

    private Double carbonFootPrint;

}
