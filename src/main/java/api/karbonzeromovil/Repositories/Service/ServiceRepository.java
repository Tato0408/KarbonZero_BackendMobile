package api.karbonzeromovil.Repositories.Service;

import api.karbonzeromovil.Entities.Service.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, String> {
    Page<ServiceEntity> findAll(Pageable pageable);
    List<ServiceEntity> findByNameContains (String serviceName);
    Page<ServiceEntity> findByNameContains (String serviceName, Pageable pageable);
}
