package api.karbonzeromovil.Repositories.Company;

import api.karbonzeromovil.Entities.Company.ChannelUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelUserRepository extends JpaRepository<ChannelUserEntity, String> {
}
