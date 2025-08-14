package api.karbonzeromovil.Repositories.Car;

import api.karbonzeromovil.Entities.Car.CarSizeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarSizeRepository extends JpaRepository<CarSizeEntity, String> {
    Page<CarSizeEntity> findAll (Pageable pageable);
}
