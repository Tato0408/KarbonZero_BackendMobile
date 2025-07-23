package api.karbonzeromovil.Entities.Company;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "TBCHANNELS")
@Setter @Getter @EqualsAndHashCode
public class ChannelEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDCHANNEL")
    private String idChannel;
    @Column(name = "IDCOMPANY", nullable = true, unique = true)
    private String idCompany;
    @Column(name = "NAME", unique = true)
    private String name;

    @OneToMany(mappedBy = "idChannel")
    private List<ChannelUserEntity> idChannelUserList;
}
