package api.karbonzeromovil.Repositories.Transport;

import api.karbonzeromovil.Entities.Transport.TransportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportRepository extends JpaRepository<TransportEntity,String> {
}
