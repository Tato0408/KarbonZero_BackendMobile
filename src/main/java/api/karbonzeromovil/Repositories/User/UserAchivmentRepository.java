package api.karbonzeromovil.Repositories.User;

import api.karbonzeromovil.Entities.User.UserAchivmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAchivmentRepository  extends JpaRepository<UserAchivmentEntity, String> {
}
