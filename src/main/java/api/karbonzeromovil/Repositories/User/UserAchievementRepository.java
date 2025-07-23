package api.karbonzeromovil.Repositories.User;

import api.karbonzeromovil.Entities.User.UserAchievementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAchievementRepository extends JpaRepository<UserAchievementEntity, String> {
}
