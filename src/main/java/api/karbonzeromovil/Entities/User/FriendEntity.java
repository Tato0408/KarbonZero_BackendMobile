package api.karbonzeromovil.Entities.User;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TBFRIENDS")
@Getter @Setter @ToString @EqualsAndHashCode
public class FriendEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDFRIEND")
    private String idFriend;
    @Column(name = "IDUSER1", nullable = false)
    private String user1;
    @Column(name = "IDUSER2", nullable = false)
    private String user2;
}
