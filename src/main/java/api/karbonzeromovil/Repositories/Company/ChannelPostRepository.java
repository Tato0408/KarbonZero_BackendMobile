package api.karbonzeromovil.Repositories.Company;

import api.karbonzeromovil.Entities.Company.ChannelPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelPostRepository extends JpaRepository<ChannelPostEntity, String> {
    List<ChannelPostEntity> findByIsApproved(Integer isApproved);
}
