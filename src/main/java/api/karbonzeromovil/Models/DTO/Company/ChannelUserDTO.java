package api.karbonzeromovil.Models.DTO.Company;

import api.karbonzeromovil.Entities.Company.ChannelEntity;
import api.karbonzeromovil.Entities.User.UserEntity;
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
    private String name;
    private String channelImage;

    @NotNull
    private String idUser;
}
