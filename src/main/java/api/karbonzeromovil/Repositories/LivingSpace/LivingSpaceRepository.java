package api.karbonzeromovil.Repositories.LivingSpace;

import api.karbonzeromovil.Entities.Food.FoodCategoryEntity;
import api.karbonzeromovil.Entities.LivingSpace.LivingSpaceEntity;
import api.karbonzeromovil.Models.DTO.LivingSpace.LivingSpaceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivingSpaceRepository extends JpaRepository<LivingSpaceEntity, String > {
    Page<LivingSpaceEntity> findAll (Pageable pagebale);
}
