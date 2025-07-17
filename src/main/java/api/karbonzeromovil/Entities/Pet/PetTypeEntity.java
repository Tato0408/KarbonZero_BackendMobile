package api.karbonzeromovil.Entities.Pet;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Entity
@Table(name="TBPETTYPES")
@Getter @Setter
@ToString @EqualsAndHashCode
public class PetTypeEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDPetType")
    private String idPetType;

    @Column(name="NAME")
    private String namePetType;

    @Column(name = "CARBONFOOTPRINT")
    private Double carbonFootPrint;

    @OneToMany(mappedBy = "idPetType")
    private Set<UserPetEntity> userPetSet;
}
