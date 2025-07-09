package api.karbonzeromovil.Repositories.LivingSpace;

import api.karbonzeromovil.Entities.LivingSpace.LivingSpaceEntity;
import api.karbonzeromovil.Models.DTO.LivingSpace.LivingSpaceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivingSpaceRepository extends JpaRepository<LivingSpaceEntity, String > {
}
