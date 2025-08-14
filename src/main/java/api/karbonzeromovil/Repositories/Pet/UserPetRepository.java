package api.karbonzeromovil.Repositories.Pet;

import api.karbonzeromovil.Entities.Pet.UserPetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPetRepository extends JpaRepository<UserPetEntity, String> {
    Page<UserPetEntity> findAll (Pageable pagebale);
}
