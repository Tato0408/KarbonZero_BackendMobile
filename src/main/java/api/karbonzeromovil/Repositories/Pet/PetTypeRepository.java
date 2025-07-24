package api.karbonzeromovil.Repositories.Pet;

import api.karbonzeromovil.Entities.Pet.PetTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetTypeRepository extends JpaRepository<PetTypeEntity, String> {
}
