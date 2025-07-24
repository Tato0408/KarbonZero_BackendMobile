package api.karbonzeromovil.Repositories.Service;

import api.karbonzeromovil.Entities.Service.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, String> {
}
