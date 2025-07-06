package api.karbonzeromovil.Repositories.Food;
import api.karbonzeromovil.Entities.Food.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, String> {
}