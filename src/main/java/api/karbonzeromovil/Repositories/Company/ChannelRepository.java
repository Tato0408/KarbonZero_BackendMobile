package api.karbonzeromovil.Repositories.Company;

import api.karbonzeromovil.Entities.Company.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity, String> {
}
