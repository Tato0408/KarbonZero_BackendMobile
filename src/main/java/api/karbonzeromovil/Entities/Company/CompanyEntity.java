package api.karbonzeromovil.Entities.Company;

import api.karbonzeromovil.Entities.User.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "TBCOMPANIES")
@Setter @Getter @EqualsAndHashCode

public class CompanyEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDCOMPANY")
    private String idCompany;
    @Column(name = "NAME", nullable = true, unique = true)
    private String name;
    @Column(name = "DOMAIN", nullable = true, unique = true)
    private String domain;

    @OneToOne(mappedBy = "idCompany")
    private ChannelEntity idCompanyChannel;
}