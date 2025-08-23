package api.karbonzeromovil.Entities.Company;

import api.karbonzeromovil.Entities.User.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TBCHANNELSUSERS")
@Setter @Getter @EqualsAndHashCode

public class ChannelUserEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")

    @Column(name="IDCHANNELUSER")
    private String idChannelUser;

    @ManyToOne
    @JoinColumn(name = "IDCHANNEL", referencedColumnName = "IDCHANNEL")
    private ChannelEntity idChannel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDUSER", referencedColumnName = "IDUSER")
    @JsonIgnore
    private UserEntity idUser;
}
