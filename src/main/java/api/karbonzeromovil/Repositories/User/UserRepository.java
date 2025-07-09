package api.karbonzeromovil.Repositories.User;

import api.karbonzeromovil.Entities.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String > {
}
