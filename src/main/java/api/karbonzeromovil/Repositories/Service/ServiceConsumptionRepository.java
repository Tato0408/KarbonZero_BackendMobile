package api.karbonzeromovil.Repositories.Service;

import api.karbonzeromovil.Entities.Service.ServiceConsumptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceConsumptionRepository extends JpaRepository<ServiceConsumptionEntity, String> {
}
