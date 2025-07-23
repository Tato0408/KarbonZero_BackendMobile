package api.karbonzeromovil.Models.DTO.Service;

import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter @Setter
public class ServiceDTO {
    private String idService;
    @Size(max = 200)
    private String name;
}
