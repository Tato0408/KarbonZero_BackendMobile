package api.karbonzeromovil.Repositories.LivingSpace;

import api.karbonzeromovil.Entities.Food.FoodCategoryEntity;
import api.karbonzeromovil.Entities.LivingSpace.LivingSpaceTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivingSpaceTypeRepository extends JpaRepository<LivingSpaceTypeEntity, String > {
    Page<LivingSpaceTypeEntity> findAll (Pageable pagebale);
}
