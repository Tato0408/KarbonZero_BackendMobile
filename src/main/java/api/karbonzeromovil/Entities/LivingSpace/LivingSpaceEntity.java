package api.karbonzeromovil.Entities.LivingSpace;

import api.karbonzeromovil.Entities.User.UserEntity;
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

    @ManyToOne
    @JoinColumn(name="IDLIVINGSPACETYPE", nullable = false, referencedColumnName = "IDLIVINGSPACETYPE")
    private LivingSpaceTypeEntity livingSpaceType;

    @ManyToOne
    @JoinColumn(name="IDUSER", nullable = false, referencedColumnName = "IDUSER")
    private UserEntity idUser;
}
