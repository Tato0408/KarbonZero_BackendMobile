package api.karbonzeromovil.Models.DTO.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter @Setter
public class CountryDTO {
    private String idCountry;
    @NotBlank()
    @Size(max = 100)
    private String name;
    @Positive
    @NotNull
    private Double carbonFootprint;
}
