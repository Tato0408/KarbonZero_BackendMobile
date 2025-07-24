package api.karbonzeromovil.Models.DTO.Company;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString   @EqualsAndHashCode
@Getter @Setter
public class ChannelUserDTO {
    private String idChannelUser;

    @NotNull
    private String idChannel;

    @NotNull
    private String idUser;
}
