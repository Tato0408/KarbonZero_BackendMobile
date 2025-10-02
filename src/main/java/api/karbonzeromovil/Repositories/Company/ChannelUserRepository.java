package api.karbonzeromovil.Repositories.Company;

import api.karbonzeromovil.Entities.Company.ChannelUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelUserRepository extends JpaRepository<ChannelUserEntity, String> {
    Page<ChannelUserEntity> findAll (Pageable pageable);
    ChannelUserEntity findByIdChannel_IdChannelAndIdUser_IdUser (String idChannel, String idUser);
}
