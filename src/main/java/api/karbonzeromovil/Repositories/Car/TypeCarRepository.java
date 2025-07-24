package api.karbonzeromovil.Repositories.Car;

import api.karbonzeromovil.Entities.Car.TypeCarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeCarRepository extends JpaRepository<TypeCarEntity, String> {
}
