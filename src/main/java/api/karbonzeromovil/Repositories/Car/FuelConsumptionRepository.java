package api.karbonzeromovil.Repositories.Car;

import api.karbonzeromovil.Entities.Car.FuelConsumptionEntity;
import api.karbonzeromovil.Models.DTO.Car.FuelConsumptionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelConsumptionRepository extends JpaRepository<FuelConsumptionEntity, String> {
}
