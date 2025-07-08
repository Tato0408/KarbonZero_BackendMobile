package api.karbonzeromovil.Entities.LivingSpace;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TBLIVINGSPACETYPES")
@Getter @Setter
@ToString @EqualsAndHashCode
public class LivingSpaceTypeEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDLIVINGSPACETYPE")
    private String idLivingSpaceType;

    @Column(name="NAME")
    private String nameLivingSpaceType;

    @Column(name = "CARBONFOOTPRINT")
    private Double carbonFootPrint;

}
