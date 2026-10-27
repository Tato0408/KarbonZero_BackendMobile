package api.karbonzeromovil.Repositories.User;

import api.karbonzeromovil.Entities.User.CountryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity,String> {
    Page<CountryEntity> findAll(Pageable pageable);
    Page<CountryEntity> findByNameContains(String name, Pageable pageable);
}
