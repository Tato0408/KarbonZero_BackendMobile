package api.karbonzeromovil.Models.DTO.Pet;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @EqualsAndHashCode
public class UserPetDTO {
    private String idUserPet;

    @NotBlank
    private String idPetType;

    @NotBlank
    private String idUser;
}
