package api.karbonzeromovil.Services.User;

import api.karbonzeromovil.Config.Argon2PasswordEncoder;
import api.karbonzeromovil.Entities.User.UserEntity;
import api.karbonzeromovil.Models.DTO.User.UserDTO;
import api.karbonzeromovil.Repositories.User.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository repo;


    public List<UserDTO> getAllUsers(){
        List<UserEntity> users = repo.findAll();
        return users.stream()
                .map(this::UsersToDTO)
                .collect(Collectors.toList());
    }
    public UserDTO insertUser(UserDTO dto) throws IllegalAccessException {
        if(dto == null || dto.getPasswordUser() == null || dto.getPasswordUser().isEmpty()){
            throw new IllegalAccessException("User cannot be null or empty");
        }
        try {
            Argon2PasswordEncoder argon = new Argon2PasswordEncoder();
            String hashedPassword = argon.HashPassword(dto.getPasswordUser());
            dto.setPasswordUser(hashedPassword);

            UserEntity entity = DT0ToUser(dto);
            UserEntity savedUser = repo.save(entity);
            return UsersToDTO(savedUser);
        } catch (Exception e) {
            throw new RuntimeException("The User could not be created"+ e.getMessage());
        }
    }
    public UserDTO updateUser(String id, UserDTO dto){
        try {
            if (repo.existsById((id))){
                UserEntity entity = repo.getReferenceById(id);
                entity.setIdCompany(dto.getIdCompany());
                entity.setIdCountry(dto.getIdCountry());
                entity.setUserName(dto.getUserName());
                entity.setFirstName(dto.getFirstName());
                entity.setLastName(dto.getLastName());
                entity.setEmail(dto.getEmail());
                entity.setImagePath(dto.getImagePath());

                if (dto.getPasswordUser() !=null){
                    Argon2PasswordEncoder argon = new Argon2PasswordEncoder();
                    String hashedPassword = argon.HashPassword(dto.getPasswordUser());
                    entity.setPasswordUser(hashedPassword);
                }
                UserEntity savedUser = repo.save(entity);
                return UsersToDTO(savedUser);
            }
            throw new IllegalArgumentException("The User could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The User could not be updated" + e.getMessage());
        }
    }
    public boolean deleteUser(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The User with the ID : " + id + " doesn't exists", 1);
        }
    }
    private UserDTO UsersToDTO(UserEntity entity){
        UserDTO dto = new UserDTO();
        dto.setIdUser(entity.getIdUser());
        dto.setIdCompany(entity.getIdCompany());
        dto.setIdCountry(entity.getIdCountry());
        dto.setUserName(entity.getUserName());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPasswordUser(entity.getPasswordUser());
        dto.setImagePath(entity.getImagePath());
        return dto;
    }
    private UserEntity DT0ToUser(UserDTO dto){
        UserEntity entity = new UserEntity();
        entity.setIdUser(dto.getIdUser());
        entity.setIdCompany(dto.getIdCompany());
        entity.setIdCountry(dto.getIdCountry());
        entity.setUserName(dto.getUserName());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPasswordUser(dto.getPasswordUser());
        entity.setImagePath(dto.getImagePath());
        return entity;
    }
}
