package api.karbonzeromovil.Repositories.Food;

import api.karbonzeromovil.Entities.Food.FoodCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategoryEntity, String > {
    Page<FoodCategoryEntity> findAll (Pageable pagebale);
}
