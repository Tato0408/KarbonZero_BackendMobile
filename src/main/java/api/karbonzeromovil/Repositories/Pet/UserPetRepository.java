package api.karbonzeromovil.Repositories.Pet;

import api.karbonzeromovil.Entities.Pet.UserPetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPetRepository extends JpaRepository<UserPetEntity, String> {
}
