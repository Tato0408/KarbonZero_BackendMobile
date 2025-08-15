package api.karbonzeromovil.Models.DTO.Company;

import api.karbonzeromovil.Entities.Company.CompanyEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString   @EqualsAndHashCode
@Getter @Setter
public class ChannelDTO {
    private String idChannel;

    @NotNull
    private CompanyEntity idCompany;

    @NotBlank
    @Size(max = 200)
    private String name;
}
