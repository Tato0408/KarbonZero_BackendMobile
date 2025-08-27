package api.karbonzeromovil.Repositories.User;

import api.karbonzeromovil.Entities.User.FriendEntity;
import api.karbonzeromovil.Entities.User.UserEntity;
import api.karbonzeromovil.Models.DTO.User.FriendDTO;
import api.karbonzeromovil.Models.DTO.User.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, String> {
    Page<FriendEntity> findAll (Pageable pageable);
    int countByUser1_IdUser(String userId);
    int countByUser2_IdUser(String userId);
    boolean existsByUser1_IdUserAndUser2_IdUser(String user1Id, String user2Id);
    FriendEntity findByUser1_IdUserAndUser2_IdUser(String user1Id, String user2Id);


}
