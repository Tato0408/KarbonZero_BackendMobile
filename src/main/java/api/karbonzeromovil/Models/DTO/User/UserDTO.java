package api.karbonzeromovil.Models.DTO.User;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@ToString
@Getter @Setter @EqualsAndHashCode
public class UserDTO {

    private String idUser;
    private String idCountry;
    private String countryName;
    @NotBlank
    @Size(max = 200)
    private String userName;
    @Size(max = 200)
    private String firstName;
    @Size(max = 200)
    private String lastName;
    @NotBlank
    @Size(max = 200)
    private String email;
    @Size(max = 200)
    private String passwordUser;
    @NotNull
    @Min(0)
    @Max(1)
    private Integer status;
    @Size(max = 200)
    private String imagePath;
}
