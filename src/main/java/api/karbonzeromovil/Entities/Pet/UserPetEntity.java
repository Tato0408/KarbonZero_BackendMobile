package api.karbonzeromovil.Entities.Pet;

import api.karbonzeromovil.Entities.User.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TBUSERPETS")
@Getter @Setter
@ToString @EqualsAndHashCode
public class UserPetEntity {

    @Id
    @GenericGenerator(name="db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDUSERPET")
    private String idUserPet;

    @ManyToOne
    @JoinColumn(name="IDPETTYPE", nullable = false)
    private PetTypeEntity idPetType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="IDUSER", nullable = false)
    @JsonIgnore
    private UserEntity idUser;
}
