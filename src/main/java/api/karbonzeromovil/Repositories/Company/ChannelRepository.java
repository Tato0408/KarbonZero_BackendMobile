package api.karbonzeromovil.Repositories.Company;

import api.karbonzeromovil.Entities.Company.ChannelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity, String> {
    Page<ChannelEntity> findAll (Pageable pageable);
}
