package api.karbonzeromovil.Services.Auth;

import api.karbonzeromovil.Config.Argon2PasswordEncoder;
import api.karbonzeromovil.Entities.User.UserEntity;
import api.karbonzeromovil.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

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
}
