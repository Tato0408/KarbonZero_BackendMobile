package api.karbonzeromovil.Repositories.User;

import api.karbonzeromovil.Entities.User.FriendEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, String> {
    Page<FriendEntity> findAll (Pageable pageable);
}
