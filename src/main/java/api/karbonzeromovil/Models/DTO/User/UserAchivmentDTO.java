package api.karbonzeromovil.Models.DTO.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class UserAchivmentDTO {
    private String idAchivment;
    @NotBlank
    private String idUser;
    @NotBlank
    @Size(max = 100)
    private String achivmentName;
}
