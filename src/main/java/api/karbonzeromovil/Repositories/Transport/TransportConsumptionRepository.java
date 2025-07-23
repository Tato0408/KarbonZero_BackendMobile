package api.karbonzeromovil.Repositories.Transport;

import api.karbonzeromovil.Entities.Transport.TransportConsumptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportConsumptionRepository extends JpaRepository<TransportConsumptionEntity,String> {
}
