package api.karbonzeromovil.Entities.User;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="TBCOUNTRIES")
@Getter @Setter @ToString @EqualsAndHashCode
public class CountryEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDCOUNTRY")
    private String idCountry;
    @Column(name = "NAME",nullable = false,unique = true,length = 100)
    private String name;
    @Column(name = "CARBONFOOTPRINT", nullable = false)
    private Double carbonFootprint;

    @OneToMany(mappedBy = "idCountry")
    private List<UserEntity> consumptionList = new ArrayList<>();
}
