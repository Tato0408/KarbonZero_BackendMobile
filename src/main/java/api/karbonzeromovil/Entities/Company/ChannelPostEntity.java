package api.karbonzeromovil.Entities.Company;

import api.karbonzeromovil.Entities.User.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TBCHANNELPOST")
@Getter @Setter @ToString @EqualsAndHashCode
public class ChannelPostEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDCHANNELPOST")
    private String idChannelPost;
    @Column(name="TITLE")
    private String title;
    @Column(name="DESCRIPT")
    private String descript;
    @Column(name="IMAGEPATH")
    private String imagePath;
    @Column(name="ISAPPROVED")
    private Integer isApproved;
    @ManyToOne
    @JoinColumn(name = "IDCHANNEL")
    private ChannelEntity idChannel;
    @ManyToOne
    @JoinColumn(name = "IDUSER")
    private UserEntity idUser;
}
