package api.karbonzeromovil.Repositories.User;

import api.karbonzeromovil.Entities.User.UserAchievementEntity;
import api.karbonzeromovil.Entities.User.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String > {
    Page<UserEntity> findAll(Pageable pageable);
    List<UserEntity> findByUserNameContainingIgnoreCase(String username);
}
