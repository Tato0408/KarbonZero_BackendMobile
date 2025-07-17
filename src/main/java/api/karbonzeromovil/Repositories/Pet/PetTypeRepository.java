package api.karbonzeromovil.Repositories.Pet;

import api.karbonzeromovil.Entities.Pet.PetTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetTypeRepository extends JpaRepository<PetTypeEntity, String> {
}
