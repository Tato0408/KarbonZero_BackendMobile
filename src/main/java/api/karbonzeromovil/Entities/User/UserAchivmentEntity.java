package api.karbonzeromovil.Entities.User;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name="TBUSERACHIVMENTS")
@Getter
@Setter
@EqualsAndHashCode
public class UserAchivmentEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDUSERACHIVMENT")
    private String idAchivment;
    @ManyToOne
    @JoinColumn(name = "IDUSER")
    private UserEntity idUser;
    @Column(name = "ACHIVMENTNAME", nullable = false)
    private String achivmentName;

}
