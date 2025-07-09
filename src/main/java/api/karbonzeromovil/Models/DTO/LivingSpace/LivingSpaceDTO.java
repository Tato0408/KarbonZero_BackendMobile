package api.karbonzeromovil.Models.DTO.LivingSpace;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString   @EqualsAndHashCode
public class LivingSpaceDTO {
    @Getter @Setter
    private String idLivingSpace;

    @NotBlank
    private String idLivingSpaceType;

    @NotBlank
    private String idUser;
}
