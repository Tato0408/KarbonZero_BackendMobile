package api.karbonzeromovil.Repositories.Car;

import api.karbonzeromovil.Entities.Car.FuelConsumptionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelConsumptionRepository extends JpaRepository<FuelConsumptionEntity, String> {
    Page<FuelConsumptionEntity> findAll (Pageable pageable);
    int countByIdTypeCar_IdTypeCar(String userId);
    List<FuelConsumptionEntity> findByLongitudeBetweenAndLatitudeBetween(Double minX, Double maxX, Double minY, Double maxY);
}
