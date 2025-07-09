package api.karbonzeromovil.Entities.LivingSpace;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TBLIVINGSPACE")
@ToString @EqualsAndHashCode
@Getter @Setter
public class LivingSpaceEntity {

    @Id
    @GenericGenerator(name="db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDLIVINGSPACE")
    private String idLivingSpace;

    @Column(name="IDLIVINGSPACETYPE")
    private String idLivingSpaceType;

    @Column(name="IDUSER")
    private String idUser;
}
