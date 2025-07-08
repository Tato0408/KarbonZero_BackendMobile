package api.karbonzeromovil.Repositories.Food;

import api.karbonzeromovil.Entities.Food.FoodConsumptionFoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodConsumptionFoodRepository extends JpaRepository<FoodConsumptionFoodEntity, String > {
}
