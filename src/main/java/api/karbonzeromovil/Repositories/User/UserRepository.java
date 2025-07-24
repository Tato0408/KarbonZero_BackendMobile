package api.karbonzeromovil.Repositories.User;

import api.karbonzeromovil.Entities.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String > {
}
