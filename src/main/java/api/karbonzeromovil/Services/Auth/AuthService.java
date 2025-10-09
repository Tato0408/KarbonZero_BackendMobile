package api.karbonzeromovil.Services.Auth;

import api.karbonzeromovil.Config.Argon2PasswordEncoder;
import api.karbonzeromovil.Entities.Company.CompanyEntity;
import api.karbonzeromovil.Entities.User.UserEntity;
import api.karbonzeromovil.Models.DTO.Company.CompanyDTO;
import api.karbonzeromovil.Repositories.Company.CompanyRepository;
import api.karbonzeromovil.Repositories.User.UserRepository;
import api.karbonzeromovil.Services.Company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserRepository repo;
    @Autowired
    private CompanyRepository companyRepo;

    public boolean Login (String email, String password) {
        Argon2PasswordEncoder objHash = new Argon2PasswordEncoder();
        UserEntity user = repo.findByEmailAndDisabled(email, 0);

        if (user != null) {
            return objHash.decodePassword(user.getPasswordUser(), password);
        }
        return false;
    }

    public UserEntity getUser (String email ) {
       return repo.findByEmail((email));
    }

    public CompanyEntity getCompany(String apiKey, String domain) {
        return companyRepo.findByApiKeyAndDomain(apiKey, domain);
    }

    public CompanyEntity getCompanyByApiKey(String apiKey) {
        return companyRepo.findByApiKey(apiKey);
    }
}
