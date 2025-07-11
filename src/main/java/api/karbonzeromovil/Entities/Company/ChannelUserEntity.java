package api.karbonzeromovil.Entities.Company;

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
    @Column(name = "IDCHANNEL")
    private String idChannel;
    @Column(name = "IDUSER")
    private String idUser;
}
