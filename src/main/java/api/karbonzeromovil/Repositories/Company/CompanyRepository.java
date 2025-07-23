package api.karbonzeromovil.Repositories.Company;

import api.karbonzeromovil.Entities.Company.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {
}
