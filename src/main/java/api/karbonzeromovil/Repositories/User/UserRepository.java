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
    UserEntity findByEmail(String email);
    UserEntity findByEmailAndDisabled(String email, int i);
    UserEntity getReferenceByIdUserAndStatus(String id, int i);
    Page<UserEntity> findAll(Pageable pageable);
    Page<UserEntity> findByStatus(int i, Pageable pageable);
    Page<UserEntity> findByUserNameContains(String name, Pageable pageable);
    Page<UserEntity> findByUserNameContainsAndStatus(String name, int i, Pageable pageable);
    List<UserEntity> findByUserNameContainingIgnoreCase(String username);
}
