package api.karbonzeromovil.Repositories.User;

import api.karbonzeromovil.Entities.User.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity,String> {
}
