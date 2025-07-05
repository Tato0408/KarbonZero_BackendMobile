package api.karbonzeromovil.Repositories.Food;

import api.karbonzeromovil.Entities.Food.FoodCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategoryEntity, String> {

}
