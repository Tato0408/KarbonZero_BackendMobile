package api.karbonzeromovil.Models.DTO.User;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@ToString
@Getter @Setter @EqualsAndHashCode
public class UserDTO {

    private String idUser;
    private String idCompany;
    private String companyName;
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
    @NotBlank
    @Size(max = 200)
    private String passwordUser;
    @Size(max = 200)
    private String imagePath;
}
