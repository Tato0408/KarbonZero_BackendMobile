package api.karbonzeromovil.Services.User;

import api.karbonzeromovil.Config.Argon2PasswordEncoder;
import api.karbonzeromovil.Entities.Company.CompanyEntity;
import api.karbonzeromovil.Entities.User.CountryEntity;
import api.karbonzeromovil.Entities.User.UserAchievementEntity;
import api.karbonzeromovil.Entities.User.UserEntity;
import api.karbonzeromovil.Models.DTO.Food.FoodConsumptionDTO;
import api.karbonzeromovil.Models.DTO.Food.FoodConsumptionFoodDTO;
import api.karbonzeromovil.Models.DTO.LivingSpace.LivingSpaceDTO;
import api.karbonzeromovil.Models.DTO.Pet.UserPetDTO;
import api.karbonzeromovil.Models.DTO.Service.ServiceConsumptionDTO;
import api.karbonzeromovil.Models.DTO.User.FriendDTO;
import api.karbonzeromovil.Models.DTO.User.UserAchievementDTO;
import api.karbonzeromovil.Models.DTO.User.UserDTO;
import api.karbonzeromovil.Repositories.Company.CompanyRepository;
import api.karbonzeromovil.Repositories.User.CountryRepository;
import api.karbonzeromovil.Repositories.User.UserAchievementRepository;
import api.karbonzeromovil.Repositories.User.UserRepository;
import api.karbonzeromovil.Services.Food.FoodConsumptionFoodService;
import api.karbonzeromovil.Services.Food.FoodConsumptionService;
import api.karbonzeromovil.Services.LivingSpace.LivingSpaceService;
import api.karbonzeromovil.Services.Pet.UserPetService;
import api.karbonzeromovil.Services.Service.ServiceConsumptionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.hibernate.boot.model.internal.CreateKeySecondPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository repo;
    @Autowired
    private CompanyRepository companyRepo;
    @Autowired
    private CountryRepository countryRepo;
    @Autowired
    private UserAchievementRepository achievementRepository;

    public Page<UserDTO> getAllUsers(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<UserEntity> users = repo.findAll(pageable);
        return users.map(this::UsersToDTO);
    }
    public UserDTO getUserById(String id){
        try {
            if(repo.existsById(id)){
                UserEntity entity = repo.getReferenceById(id);
                return UsersToDTO(entity);
            }
            throw new IllegalArgumentException("The User with the id: "+id+" does not exist, check if it exist");
        }catch (Exception err) {
            throw new RuntimeException("The User could not be found" + err.getMessage());

        }
    }
    /// Aca van todos los gets x de users asi genericos
    public List<FriendDTO> getAllFriendsMainUser(String id){
        UserEntity user = repo.getReferenceById(id);
        return user.getFriendsByMainUser().stream()
                .map(new FriendService()::FriendToDTO).collect(Collectors.toList());
    }
    public List<FriendDTO> getAllFriendsSecondaryUser(String id){
        UserEntity user = repo.getReferenceById(id);
        return user.getFriendsBySecondaryUser().stream()
                .map(new FriendService()::FriendToDTO).collect(Collectors.toList());
    }
    public List<UserAchievementDTO> getAllUserAchievements(String userId) {
        UserEntity user = repo.getReferenceById(userId);
        return user.getAllUserAchievements().stream()
                .map(new UserAchievementService()::AchievementToDTO).collect(Collectors.toList());
    }


    ///Aca van todos los de gets de x cosnumo
    public List<LivingSpaceDTO> getAllLivingSpacesUser (String id){
        UserEntity user = repo.getReferenceById(id);
        return user.getLivingSpacesUser().stream()
                .map(new LivingSpaceService()::livingSpaceToDTO).collect(Collectors.toList());
    }

    public List<FoodConsumptionDTO> getAllFoodConsumptionsUser(String id){
        UserEntity user = repo.getReferenceById(id);
        return user.getFoodConsumpionsUser().stream()
                .map(new FoodConsumptionService()::foodConsumptionToDTO).collect(Collectors.toList());
    }

    public List<UserPetDTO> getAllUserPets (String id){
        UserEntity user = repo.getReferenceById(id);
        return user.getAllUserPets().stream().map(new UserPetService()::UserPetToDTO).collect(Collectors.toList());
    }

//    public List<ServiceConsumptionDTO> getAllUserServiceConsumptions(String id){
//        UserEntity user = repo.getReferenceById(id);
//        return  user.getAllUserServiceConsumption().stream().map(new ServiceConsumptionService()::ServiceConsumptionToDTO).collect(Collectors.toList());
//    }


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
                entity.setIdCompany(companyRepo.getReferenceById(dto.getIdCompany()));
                entity.setIdCountry(countryRepo.getReferenceById(dto.getIdCountry()));
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
        dto.setUserName(entity.getUserName());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPasswordUser(entity.getPasswordUser());
        dto.setImagePath(entity.getImagePath());
        if(entity.getIdCompany() != null && entity.getIdCountry() != null){
            dto.setIdCompany(entity.getIdCompany().getIdCompany());
            dto.setIdCountry(entity.getIdCountry().getIdCountry());
            dto.setCountryName(entity.getIdCountry().getName());
            dto.setCompanyName(entity.getIdCompany().getName());
        }else{
            dto.setIdCompany(null);
            dto.setIdCountry(null);
            dto.setCompanyName("No hay una compañia asociada");
            dto.setIdCountry("No hay un pais asociado");
        }
        return dto;
    }
    private UserEntity DT0ToUser(UserDTO dto){
        UserEntity entity = new UserEntity();
        entity.setIdUser(dto.getIdUser());
        entity.setUserName(dto.getUserName());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPasswordUser(dto.getPasswordUser());
        entity.setImagePath(dto.getImagePath());
        if(dto.getIdCompany() != null && dto.getIdCountry() != null){
            CompanyEntity company = companyRepo.findById(dto.getIdCompany()).orElseThrow(()-> new IllegalArgumentException("No hay una compañia con ese id"+dto.getIdCountry()));
            CountryEntity country = countryRepo.findById(dto.getIdCountry()).orElseThrow(()-> new IllegalArgumentException("No hay un pais con ese id"+ dto.getIdCountry()));
            entity.setIdCompany(company);
            entity.setIdCountry(country);
        }
        return entity;
    }
}
