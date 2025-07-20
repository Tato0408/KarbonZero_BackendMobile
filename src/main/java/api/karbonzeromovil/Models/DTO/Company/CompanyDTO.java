package api.karbonzeromovil.Models.DTO.Company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString   @EqualsAndHashCode
@Getter @Setter
public class CompanyDTO {

    private String idCompany;

    @NotBlank()
    @Size(max = 200)
    private String name;

    @NotBlank()
    @Size(max = 200)
    private String domain;
}
