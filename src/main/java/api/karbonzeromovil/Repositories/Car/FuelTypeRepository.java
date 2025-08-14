package api.karbonzeromovil.Repositories.Car;

import api.karbonzeromovil.Entities.Car.FuelTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelTypeEntity, String> {
    Page<FuelTypeEntity> findAll (Pageable pageable);
}
