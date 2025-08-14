package api.karbonzeromovil.Repositories.Food;

import api.karbonzeromovil.Entities.Food.FoodCategoryEntity;
import api.karbonzeromovil.Entities.Food.FoodConsumptionFoodEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodConsumptionFoodRepository extends JpaRepository<FoodConsumptionFoodEntity, String > {
    Page<FoodConsumptionFoodEntity> findAll (Pageable pagebale);
}
