package api.karbonzeromovil.Models.DTO.Company;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter @Setter
public class ChannelPostDTO {
    private String idChannelPost;
    @NotBlank
    private String title;
    @NotBlank
    private String descript;
    private String imagePath;
    private Integer isApproved;
    @NotBlank
    private String idChannel;
    @NotBlank
    private String idUser;
    private String userName;
}
