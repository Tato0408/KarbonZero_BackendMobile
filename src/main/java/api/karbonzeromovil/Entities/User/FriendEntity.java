package api.karbonzeromovil.Entities.User;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TBFRIENDS")
@Getter @Setter @ToString @EqualsAndHashCode
public class FriendEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDFRIEND")
    private String idFriend;
    @ManyToOne
    @JoinColumn(name = "IDUSER1", nullable = false)
    private UserEntity user1;
    @ManyToOne
    @JoinColumn(name = "IDUSER2", nullable = false)
    private UserEntity user2;
}
