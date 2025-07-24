package api.karbonzeromovil.Repositories.User;

import api.karbonzeromovil.Entities.User.UserAchievementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievementEntity, String> {
}
