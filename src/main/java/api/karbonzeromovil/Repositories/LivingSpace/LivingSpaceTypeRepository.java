package api.karbonzeromovil.Repositories.LivingSpace;

import api.karbonzeromovil.Entities.LivingSpace.LivingSpaceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivingSpaceTypeRepository extends JpaRepository<LivingSpaceTypeEntity, String > {
}
