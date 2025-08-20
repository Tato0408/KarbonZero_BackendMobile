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
    private String idLivingSpace;
    private String idLivingSpaceType;
    private String idUser;

    private String livingSpaceTypeName;

    private String userName;
}