package api.karbonzeromovil.Repositories.User;

import api.karbonzeromovil.Entities.User.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<FriendEntity, String> {
}
