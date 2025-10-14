package api.karbonzeromovil.Services.User;

import api.karbonzeromovil.Config.Argon2PasswordEncoder;
import api.karbonzeromovil.Entities.User.CountryEntity;
import api.karbonzeromovil.Entities.User.UserEntity;
import api.karbonzeromovil.Exceptions.DeleteException;
import api.karbonzeromovil.Exceptions.ExceptionNotFound;
import api.karbonzeromovil.Exceptions.UpdateException;
import api.karbonzeromovil.Models.DTO.Car.CarDTO;
import api.karbonzeromovil.Models.DTO.Company.ChannelUserDTO;
import api.karbonzeromovil.Models.DTO.Food.FoodConsumptionDTO;
import api.karbonzeromovil.Models.DTO.LivingSpace.LivingSpaceDTO;
import api.karbonzeromovil.Models.DTO.Pet.UserPetDTO;
import api.karbonzeromovil.Models.DTO.Service.ServiceConsumptionDTO;
import api.karbonzeromovil.Models.DTO.Transport.TransportConsumptionDTO;
import api.karbonzeromovil.Models.DTO.User.FriendDTO;
import api.karbonzeromovil.Models.DTO.User.UserAchievementDTO;
import api.karbonzeromovil.Models.DTO.User.UserDTO;
import api.karbonzeromovil.Repositories.Car.CarRepository;
import api.karbonzeromovil.Repositories.Car.FuelConsumptionRepository;
import api.karbonzeromovil.Repositories.Company.CompanyRepository;
import api.karbonzeromovil.Repositories.Food.FoodConsumptionRepository;
import api.karbonzeromovil.Repositories.Service.ServiceConsumptionRepository;
import api.karbonzeromovil.Repositories.Transport.TransportConsumptionRepository;
import api.karbonzeromovil.Repositories.User.CountryRepository;
import api.karbonzeromovil.Repositories.User.FriendRepository;
import api.karbonzeromovil.Repositories.User.UserAchievementRepository;
import api.karbonzeromovil.Repositories.User.UserRepository;
import api.karbonzeromovil.Services.Car.CarService;
import api.karbonzeromovil.Services.Company.ChannelUserService;
import api.karbonzeromovil.Services.Food.FoodConsumptionService;
import api.karbonzeromovil.Services.LivingSpace.LivingSpaceService;
import api.karbonzeromovil.Services.Pet.UserPetService;
import api.karbonzeromovil.Services.Service.ServiceConsumptionService;
import api.karbonzeromovil.Services.Transport.TransportConsumptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private FoodConsumptionRepository foodRepo;
    @Autowired
    private ServiceConsumptionRepository serviceRepo;
    @Autowired
    private FuelConsumptionRepository fuelRepo;
    @Autowired
    TransportConsumptionRepository transportRepo;
    @Autowired
    private CarRepository carRepo;

    public Page<UserDTO> getAllUsers(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<UserEntity> users = repo.findAll(pageable);
        return users.map(this::UsersToDTO);
    }

    public Page<UserDTO> getAllUsersWithStatus(int status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> users = repo.findByStatus(status, pageable);
        return users.map(this::UsersToDTO);
    }

    public Page<UserDTO> getUsersByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> users = repo.findByUserNameContainsAndStatus(name, 0, pageable);
        return users.map(this::UsersToDTO);
    }

    public Page<UserDTO> getAdminsByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> users = repo.findByUserNameContains(name, pageable);
        return users.map(this::UsersToDTO);
    }

    public int getAllUserConsumptions(String id){
        return foodRepo.countByIdUser_IdUser(id) + serviceRepo.countByIdUser_IdUser(id) +
                fuelRepo.countByIdTypeCar_IdTypeCar(id) + transportRepo.countByIdUser_IdUser(id);
    }

    public List<UserDTO> getAllUsersSearch(String variable){
        List<UserEntity> entity = repo.findByUserNameContainingIgnoreCase(variable);
        return entity.stream().map(this::UsersToDTO).collect(Collectors.toList());
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

    public UserDTO getAdminById(String id) {
        try {
            if (repo.existsById(id)) {
                UserEntity entity = repo.getReferenceByIdUserAndStatus(id, 1);
                return UsersToDTO(entity);
            }
            throw new ExceptionNotFound("The admin with the id: " + id + " doesn't exists");
        } catch (Exception e) {
            throw new ExceptionNotFound("Error getting the admin");
        }
    }

//    public int countByMainUser(String id){
//        UserEntity user = repo.getReferenceById(id);
//        List<FriendDTO> followers = user.getFriendsByMainUser().stream()
//                .map(new FriendService()::FriendToDTO).collect(Collectors.toList());
//        return friendRepository.countByUser1(followers);
//    }
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

    public List<ChannelUserDTO> getAllChannelUser(String id){
        UserEntity user = repo.getReferenceById(id);
        return user.getGetAllChannelsByUser().stream().map(new ChannelUserService()::ChannelUserToDTO).collect(Collectors.toList());
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
    public List<CarDTO> getAllUserCars (String id){
        UserEntity user = repo.getReferenceById(id);
        return user.getGetAllUserCars().stream().map(new CarService():: CarsToDTO).collect(Collectors.toList());
    }
    public List<ServiceConsumptionDTO> getAllUserServiceConsumptions(String id){
        UserEntity user = repo.getReferenceById(id);
        return  user.getAllUserServiceConsumption().stream().map(new ServiceConsumptionService()::ServiceConsumptionToDTO).collect(Collectors.toList());
    }
    public List<TransportConsumptionDTO> getAllTransportConsumptionsByUser(String id){
        UserEntity user = repo.getReferenceById(id);
        return  user.getAllUserTransportConsumption().stream().map(new TransportConsumptionService()::TransportConsumptionToDTO).collect(Collectors.toList());
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
                entity.setIdCountry(countryRepo.getReferenceById(dto.getIdCountry()));
                entity.setUserName(dto.getUserName());
                entity.setFirstName(dto.getFirstName());
                entity.setLastName(dto.getLastName());
                entity.setEmail(dto.getEmail());
                entity.setImagePath(dto.getImagePath());
                entity.setStatus(dto.getStatus());

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

    public Boolean confirmPassword(String idUser, String password) {
        Argon2PasswordEncoder argonObj = new Argon2PasswordEncoder();

        if (!repo.existsById(idUser)) {
            return false;
        }

        UserEntity user = repo.getReferenceById(idUser);
        String databaseHashedPassword = user.getPasswordUser();
        return argonObj.decodePassword(databaseHashedPassword, password);
    }
    public boolean deleteUser(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            throw new DeleteException("The User with the ID: " + id + " doesn't exists");
        }
    }

    public boolean updateDisabled(String id, UserDTO dto) {
        try {
            if (repo.existsById(id)) {
                UserEntity entity = repo.getReferenceById(id);
                entity.setDisabled(dto.getDisabled());
                repo.save(entity);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new UpdateException("The User with the ID: " + id + " can't be blocked");
        }
    }
    public UserDTO UsersToDTO(UserEntity entity){
        UserDTO dto = new UserDTO();
        dto.setIdUser(entity.getIdUser());
        dto.setUserName(entity.getUserName());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPasswordUser(null);
        dto.setStatus(entity.getStatus());
        dto.setDisabled(entity.getDisabled());
        dto.setImagePath(entity.getImagePath());

        if(entity.getIdCountry() != null){
            dto.setIdCountry(entity.getIdCountry().getIdCountry());
            dto.setCountryName(entity.getIdCountry().getName());
        }else{
            dto.setIdCountry(null);
            dto.setCountryName("No hay un pais asociado");
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
        entity.setStatus(dto.getStatus());
        entity.setDisabled(dto.getDisabled());
        entity.setImagePath(dto.getImagePath());

        if(dto.getIdCountry() != null){
            CountryEntity country = countryRepo.findById(dto.getIdCountry()).orElseThrow(()-> new IllegalArgumentException("No hay un pais con ese id"+ dto.getIdCountry()));
            entity.setIdCountry(country);
        }
        return entity;
    }

}
