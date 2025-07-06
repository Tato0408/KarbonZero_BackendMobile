package api.karbonzeromovil.Models.DTO.User;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter @Setter
public class FriendDTO {
    private String idFriend;
    @NotBlank
    private String user1;
    @NotBlank
    private String user2;
}
