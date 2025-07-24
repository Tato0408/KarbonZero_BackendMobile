package api.karbonzeromovil.Repositories.Car;

import api.karbonzeromovil.Entities.Car.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, String> {
}
