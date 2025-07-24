package api.karbonzeromovil.Repositories.Transport;

import api.karbonzeromovil.Entities.Transport.TransportConsumptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportConsumptionRepository extends JpaRepository<TransportConsumptionEntity,String> {
}
