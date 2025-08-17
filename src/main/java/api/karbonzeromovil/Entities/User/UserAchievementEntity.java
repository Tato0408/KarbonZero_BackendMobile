package api.karbonzeromovil.Entities.User;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TBUSERACHIEVEMENTS")
@Getter
@Setter
@EqualsAndHashCode
public class UserAchievementEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDUSERACHIEVEMENT")
    private String idAchievement;

    @ManyToOne
    @JoinColumn(name = "IDUSER", referencedColumnName = "IDUSER")
    private UserEntity idUser;
    @Column(name = "ACHIEVEMENTNAME", nullable = false)
    private String achievementName;

}
