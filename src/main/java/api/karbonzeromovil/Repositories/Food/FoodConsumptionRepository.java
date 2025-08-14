package api.karbonzeromovil.Repositories.Food;

import api.karbonzeromovil.Entities.Food.FoodCategoryEntity;
import api.karbonzeromovil.Entities.Food.FoodConsumptionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodConsumptionRepository extends JpaRepository<FoodConsumptionEntity, String> {
    Page<FoodConsumptionEntity> findAll (Pageable pagebale);
}
