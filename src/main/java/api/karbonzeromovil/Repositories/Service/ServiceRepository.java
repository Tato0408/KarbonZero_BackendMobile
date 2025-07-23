package api.karbonzeromovil.Repositories.Service;

import api.karbonzeromovil.Entities.Service.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, String> {
}
