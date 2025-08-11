package api.karbonzeromovil.Entities.User;

import api.karbonzeromovil.Entities.Company.CompanyEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TBUSERS")
@Setter @Getter @EqualsAndHashCode
public class UserEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDUSER")
    private String idUser;
    @ManyToOne
    @JoinColumn(name = "IDCOMPANY", referencedColumnName = "IDCOMPANY", nullable = true)
    private CompanyEntity idCompany;
    @ManyToOne
    @JoinColumn(name = "IDCOUNTRY", referencedColumnName = "IDCOUNTRY", nullable = true)
    private CountryEntity idCountry;
    @Column(name = "USERNAME", nullable = false)
    private String userName;
    @Column(name = "FIRSTNAME")
    private String firstName;
    @Column(name = "LASTNAME")
    private String lastName;
    @Column(name = "EMAIL", nullable = false)
    private String email;
    @Column(name = "PASSWORDUSER", nullable = false)
    private String passwordUser;
    @Column(name = "IMAGEPATH", nullable = true)
    private String imagePath;
}
