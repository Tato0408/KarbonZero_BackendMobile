package api.karbonzeromovil.Repositories.Company;

import api.karbonzeromovil.Entities.Company.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {
}
