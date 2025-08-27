package api.karbonzeromovil.Repositories.Service;

import api.karbonzeromovil.Entities.Service.ServiceConsumptionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceConsumptionRepository extends JpaRepository <ServiceConsumptionEntity, String> {
    Page<ServiceConsumptionEntity> findAll(Pageable pageable);
    int countByIdUser_IdUser(String userId);

}
