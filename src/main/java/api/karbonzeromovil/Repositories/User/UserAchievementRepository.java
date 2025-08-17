package api.karbonzeromovil.Repositories.User;

import api.karbonzeromovil.Entities.User.UserAchievementEntity;
import api.karbonzeromovil.Entities.User.UserEntity;
import api.karbonzeromovil.Models.DTO.User.UserAchievementDTO;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievementEntity, String> {
    Page<UserAchievementEntity> findAll(Pageable pageable);
}
