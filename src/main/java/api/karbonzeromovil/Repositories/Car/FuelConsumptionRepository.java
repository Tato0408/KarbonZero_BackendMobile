package api.karbonzeromovil.Repositories.Car;

import api.karbonzeromovil.Entities.Car.FuelConsumptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelConsumptionRepository extends JpaRepository<FuelConsumptionEntity, String >{
}
