package api.karbonzeromovil.Entities.Company;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "IDCOMPANY", referencedColumnName = "idCompany",nullable = true, unique = true)
    private CompanyEntity idCompany;

    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "DESCRIPT")
    private String descript;

    @Column(name = "IMAGEPATH")
    private String imagePath;

    @OneToMany(mappedBy = "idChannel")
    private List<ChannelPostEntity> allChannelChannelPost = new ArrayList<>();
}