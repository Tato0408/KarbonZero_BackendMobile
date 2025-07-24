package api.karbonzeromovil.Repositories.Transport;

import api.karbonzeromovil.Entities.Transport.TransportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportRepository extends JpaRepository<TransportEntity,String> {
}
