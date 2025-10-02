package api.karbonzeromovil.Repositories.Transport;

import api.karbonzeromovil.Entities.Transport.TransportConsumptionEntity;
import api.karbonzeromovil.Models.DTO.Transport.TransportConsumptionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportConsumptionRepository extends JpaRepository<TransportConsumptionEntity,String> {
    Page<TransportConsumptionEntity> findAll(Pageable pageable);
    int countByIdUser_IdUser(String userId);
    List<TransportConsumptionEntity> findByLongitudeBetweenAndLatitudeBetween(Double minX, Double maxX, Double minY, Double maxY);
}
