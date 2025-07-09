package api.karbonzeromovil.Models.DTO.LivingSpace;

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

    private String idLivingSpaceType;

    private String idUser;
}
