package api.karbonzeromovil.Repositories.Car;

import api.karbonzeromovil.Entities.Car.FuelTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelTypeRepository extends JpaRepository<FuelTypeEntity, String> {
}
