package api.karbonzeromovil.Repositories.Company;

import api.karbonzeromovil.Entities.Company.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {
    Page<CompanyEntity> findAll (Pageable pageable);
    Page<CompanyEntity> findByNameContains(String name, Pageable pageable);
    CompanyEntity findByApiKeyAndBackendDomain(String apiKey, String domain);
    CompanyEntity findByApiKey(String apiKey);
}
