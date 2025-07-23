package api.karbonzeromovil.Repositories.Service;

import api.karbonzeromovil.Entities.Service.ServiceConsumptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceConsumptionRepository extends JpaRepository<ServiceConsumptionEntity, String> {
}
