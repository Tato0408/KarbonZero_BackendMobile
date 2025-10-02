package api.karbonzeromovil.Controllers.User;

import api.karbonzeromovil.Entities.User.UserEntity;
import api.karbonzeromovil.Models.DTO.Car.CarDTO;
import api.karbonzeromovil.Models.DTO.Company.ChannelUserDTO;
import api.karbonzeromovil.Models.DTO.Food.FoodConsumptionDTO;
import api.karbonzeromovil.Models.DTO.LivingSpace.LivingSpaceDTO;
import api.karbonzeromovil.Models.DTO.Pet.UserPetDTO;
import api.karbonzeromovil.Models.DTO.Service.ServiceConsumptionDTO;
import api.karbonzeromovil.Models.DTO.Transport.TransportConsumptionDTO;
import api.karbonzeromovil.Models.DTO.User.CountryDTO;
import api.karbonzeromovil.Models.DTO.User.FriendDTO;
import api.karbonzeromovil.Models.DTO.User.UserAchievementDTO;
import api.karbonzeromovil.Models.DTO.User.UserDTO;
import api.karbonzeromovil.Services.User.CountryService;
import api.karbonzeromovil.Services.User.FriendService;
import api.karbonzeromovil.Services.User.UserAchievementService;
import api.karbonzeromovil.Services.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static api.karbonzeromovil.Utils.Responses.*;

@RestController
@RequestMapping("/apiUser")
@CrossOrigin(origins = " http://localhost:5173", allowCredentials = "true")
public class UserController {
    @Autowired
    private CountryService countryService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserAchievementService userAchievementService;
    @Autowired
    private UserService userService;

    //region Summary: USERCRUD
    @GetMapping("/getAllUsers")
    public ResponseEntity<Page<UserDTO>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if(size<=0 || size>50){
            ResponseEntity.badRequest().body(Map.of("status","El tamaño de la pagina debe de estar entre 1 y 50"));
            return ResponseEntity.ok(null);
        }
        Page<UserDTO> users = userService.getAllUsers(page, size);
        if(users == null){
            ResponseEntity.badRequest().body(Map.of("status", "No hay usuarios registrados "));
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/getAllUsersWithStatus/{status}")
    public ResponseEntity<Page<UserDTO>> getAllUsersWithStatus(@PathVariable int status,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size){
        if(size<=0 || size>50){
            ResponseEntity.badRequest().body(Map.of("status","El tamaño de la pagina debe de estar entre 1 y 50"));
            return ResponseEntity.ok(null);
        }
        Page<UserDTO> users = userService.getAllUsersWithStatus(status, page, size);
        if(users == null){
            ResponseEntity.badRequest().body(Map.of("status", "No hay administradores registrados "));
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/getUserById/{id}")
    public UserDTO getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @GetMapping("/getAdminById/{id}")
    public UserDTO getAdminById(@PathVariable String id){
        return userService.getAdminById(id);
    }

    @GetMapping("/getUsersByName/{name}")
    public Page<UserDTO> getUsersByName(@PathVariable String name,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size){
        return userService.getUsersByName(name, page, size);
    }

    @GetMapping("/getAdminsByName/{name}")
    public Page<UserDTO> getAdminsByName(@PathVariable String name,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size){
        return userService.getAdminsByName(name, page, size);
    }

    @PostMapping("/insertUser")
    public ResponseEntity<Map<String,Object>> insertUser(@Valid @RequestBody UserDTO dto, HttpServletRequest request){
        try {
            UserDTO answer = userService.insertUser(dto);
            if (answer == null){
                return responseBadRequest("Invalid data to create an User object");
            }
            return responseCreated(answer);
        } catch (Exception e) {
            return responseServerError(e, "Cannot create user object");
        }
    }



    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?>updateUser(@PathVariable String id, @Valid @RequestBody UserDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return responseBadRequestBinding(bindingResult);
        }
        try {
            UserDTO answer = userService.updateUser(id,dto);
            if (answer== null){
                return responseBadRequest("Invalid data to update an user object");
            }
            return responseUpdated(answer);
        }catch (Exception e){
            return responseServerError(e, "Cannot update an user object");
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable String id) {
        try {
            if(!userService.deleteUser(id)){
                return responseNotFound("user not found");
            }
            return responseOK("user deleted with success");
        } catch (Exception e) {
            return responseServerError(e, "Cannot delete the user with the User Object");
        }
    }

    @PostMapping("/updateDisabled/{id}")
    public ResponseEntity<Map<String,Object>> banUser(@PathVariable String id,  @RequestBody UserDTO dto){
        try {
            if (!userService.updateDisabled(id, dto)) {
                return responseNotFound("user not found");
            }
            return responseOK("User updated with succes");
        } catch (Exception e) {
            return responseServerError(e, "Cannot block the user object");
        }
    }

    //endregion
    //region Summary: FRIENDCRUD

    @GetMapping("/getAllFriends")
    public ResponseEntity<Page<FriendDTO>> getAllFriends(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10") int size){
        if(size <=0 || size >50){
            ResponseEntity.badRequest().body(Map.of("status","El tamaño de la pagina debe de estar entre 1 y 50"));
            return ResponseEntity.ok(null);
        }
        Page<FriendDTO> friends = friendService.getAllFriends(page, size);
        if(friends == null){
            ResponseEntity.badRequest().body(Map.of("status", "No hay amigos registrados "));
        }
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/getFriendById/{id}")
    public FriendDTO getFriendById(@PathVariable String id){
        return friendService.getFriendById(id);
    }

    @PostMapping("/insertFriend")
    public ResponseEntity<Map<String,Object>> insertFriend(@Valid @RequestBody FriendDTO dto, HttpServletRequest request){
        try {
            FriendDTO answer = friendService.insertFriend(dto);
            if (answer == null){
                return responseBadRequest("Invalid data to create a friend object");
            }
            return responseCreated(answer);
        } catch (Exception e) {
            return responseServerError(e, "Cannot create friend object");
        }
    }

    @PutMapping("/updateFriend/{id}")
    public ResponseEntity<?>updateFriend(@PathVariable String id, @Valid @RequestBody FriendDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return responseBadRequestBinding(bindingResult);
        }
        try {
            FriendDTO answer = friendService.updateFriend(id,dto);
            if (answer== null){
                return responseBadRequest("Invalid data to update a friend object");
            }
            return responseUpdated(answer);
        }catch (Exception e){
            return responseServerError(e, "Cannot update a friend object");
        }
    }

    @DeleteMapping("/deleteFriend/{id}")
    public ResponseEntity<Map<String, Object>> deleteFriend(@PathVariable String id) {
        try {
            if(!friendService.deleteFriend(id)){
                return responseNotFound("friend not found");
            }
            return responseOK("friend deleted with success");
        } catch (Exception e) {
            return responseServerError(e, "Cannot delete the friend with the friend Object");
        }
    }
    //endregion
    //region Summary: ACHIEVEMENTSCRUD

    @GetMapping("/getAllAchievements")
    public ResponseEntity<Page<UserAchievementDTO>> getAllAchievements(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if(size <=0 || size>50){
            ResponseEntity.badRequest().body(Map.of("status","El tamaño de la pagina debe de estar entre 1 y 50"));
            return ResponseEntity.ok(null);
        }
        Page<UserAchievementDTO> achievements = userAchievementService.getAllAchievements(page, size);
        if(achievements ==null){
            ResponseEntity.badRequest().body(Map.of("status", "No hay logros registrados "));
        }
        return ResponseEntity.ok(achievements);
    }

    @GetMapping("/getAchievementById/{id}")
    public UserAchievementDTO getAchievementById(@PathVariable String id){
        return userAchievementService.getAchievementById(id);
    }

    @PostMapping("/insertAchievement")
    public ResponseEntity<Map<String,Object>> insertAchievement(@Valid @RequestBody UserAchievementDTO dto, HttpServletRequest request){
        try {
            UserAchievementDTO answer = userAchievementService.insertAchievement(dto);
            if (answer == null){
                return responseBadRequest("Invalid data to create an achievement object");
            }
            return responseCreated(answer);
        } catch (Exception e) {
            return responseServerError(e, "Cannot create achievement object");
        }
    }

    @PutMapping("/updateAchievement/{id}")
    public ResponseEntity<?>updateAchievement(@PathVariable String id, @Valid @RequestBody UserAchievementDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return responseBadRequestBinding(bindingResult);
        }
        try {
            UserAchievementDTO answer = userAchievementService.updateAchievement(id,dto);
            if (answer== null){
                return responseBadRequest("Invalid data to update an achievement object");
            }
            return responseUpdated(answer);
        }catch (Exception e){
            return responseServerError(e, "Cannot update an achievement object");
        }
    }

    @DeleteMapping("/deleteAchievement/{id}")
    public ResponseEntity<Map<String, Object>> deleteAchievement(@PathVariable String id) {
        try {
            if(!userAchievementService.deleteAchievement(id)){
                return responseNotFound("Achievement not found");
            }
            return responseOK("Achievement deleted with success");
        } catch (Exception e) {
            return responseServerError(e, "Cannot delete the Achievement with the achievement Object");
        }
    }
    //endregion
    //region Summary: COUNTRIESCRUD

    @GetMapping("/getAllCountries")
    public ResponseEntity<Page<CountryDTO>> getAllCountries(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10")int size){
        if(size <=0 || size >50){
            ResponseEntity.badRequest().body(Map.of("status", "el tamaño de la pagina debe de estar entre 1 y 50"));
            return ResponseEntity.ok(null);
        }
        Page<CountryDTO> countries = countryService.getAllCountries(page,size);
        if(countries == null){
            ResponseEntity.badRequest().body(Map.of("status", "No hay logros registrados"));
        }
        return ResponseEntity.ok(countries);

    }
    @GetMapping("/getAllCountriesSP")
    public ResponseEntity<List<CountryDTO>> getAllCountriesSP(){
        List<CountryDTO> countries = countryService.getAllCountriesSP();
        if(countries == null){
            ResponseEntity.badRequest().body(Map.of("status", "No hay logros registrados"));
        }
        return ResponseEntity.ok(countries);

    }
    @GetMapping("/getCountryById/{id}")
    public CountryDTO getCountryById(@PathVariable String id){
        return countryService.getCountryById(id);
    }

    @GetMapping("/getCountryByName/{name}")
    public Page<CountryDTO> getFoodByName (@PathVariable String name,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        if (size <= 0 || size > 50) {
            return null;
        }
        return countryService.getCountryByName(name, page, size);
    }

    @PostMapping("/insertCountry")
    public ResponseEntity<Map<String,Object>> insertUser(@Valid @RequestBody CountryDTO dto, HttpServletRequest request){
        try {
            CountryDTO answer = countryService.insertCountry(dto);
            if (answer == null){
                return responseBadRequest("Invalid data to create a country object");
            }
            return responseCreated(answer);
        } catch (Exception e) {
            return responseServerError(e, "Cannot create country object");
        }
    }
    @PutMapping("/updateCountry/{id}")
    public ResponseEntity<?>updateCountry(@PathVariable String id, @Valid @RequestBody CountryDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return responseBadRequestBinding(bindingResult);
        }
        try {
            CountryDTO answer = countryService.updateCountry(id,dto);
            if (answer== null){
                return responseBadRequest("Invalid data to update a country object");
            }
            return responseUpdated(answer);
        }catch (Exception e){
            return responseServerError(e, "Cannot update a country object");
        }
    }

    @DeleteMapping("/deleteCountry/{id}")
    public ResponseEntity<Map<String, Object>> deleteCountry(@PathVariable String id) {
        try {
            if(!countryService.deleteCountry(id)){
                return responseNotFound("Country not found");
            }
            return responseOK("Country deleted with success");
        } catch (Exception e) {
            return responseServerError(e, "Cannot delete the country with the country Object");
        }
    }
    //endregion
    //region SUMMARY: All oneToManyGets
    @GetMapping("/getCountAllFollowersByUser/{id}")
    public int getCountAllFollowersByUser(@PathVariable String id)  {
        return friendService.countFollowers(id);
    }
    @GetMapping("/searchUsers/{variable}")
    public List<UserDTO> searchUsers(@PathVariable String variable) {
        return userService.getAllUsersSearch(variable);
    }
    @GetMapping("/getCountAllFollowingsByUser/{id}")
    public int getCountAllFollowingByUser(@PathVariable String id)  {
        return friendService.countFollowing(id);
    }
    @GetMapping("/getAllAchievementsByUser/{id}")
    public List<UserAchievementDTO> getAllAchivementsByUser( @PathVariable String id){
        return userService.getAllUserAchievements(id);
    }
    @GetMapping("/getAllCarsByUser/{id}")
    public List<CarDTO> getAllCarsByUser(@PathVariable String id){
        return userService.getAllUserCars(id);
    }
    @GetMapping("/getFriendsByMainUser/{id}")
    public List<FriendDTO> getFriendsByMaiNuSER( @PathVariable String id){
        return userService.getAllFriendsMainUser(id);
    }

    @GetMapping("/getAllChannelsByUser/{id}")
    public List<ChannelUserDTO> getAllChannelsByUser(@PathVariable String id){
        return userService.getAllChannelUser(id);
    }
    @GetMapping("/getFriendsBySecondaryUser/{id}")
    public List<FriendDTO> getFriendsBySecondaryUser( @PathVariable String id){
        return userService.getAllFriendsSecondaryUser(id);
    }

    @GetMapping("/getAllUserLivingConsumptions/{id}")
    public List<LivingSpaceDTO>getAllUserLivingConsumptions(@PathVariable String id){
        return userService.getAllLivingSpacesUser(id);
    }
    @GetMapping("/getAllTransportConsumptionsByUser/{id}")
    public List<TransportConsumptionDTO>getAllTransportConsumptionsByUser(@PathVariable String id){
        return userService.getAllTransportConsumptionsByUser(id);
    }

    @GetMapping("/getAllUserFoodConsumptions/{id}")
    public List<FoodConsumptionDTO>getAllUserFoodConsumptions(@PathVariable String id){
        return userService.getAllFoodConsumptionsUser(id);
    }

    @GetMapping("/getAllPetsByUser/{id}")
    public List<UserPetDTO> getAllUserPetsById(@PathVariable String id){
        return userService.getAllUserPets(id);
    }


    @GetMapping("/getExistRelation/{idUser1}/{idUser2}")
    public boolean getExistRelation(@PathVariable String idUser1, @PathVariable String idUser2){
        return friendService.getExistRelationUser1AndUser2(idUser1, idUser2);
    }
    @GetMapping("/getRelation/{idUser1}/{idUser2}")
    public FriendDTO getRelation(@PathVariable String idUser1, @PathVariable String idUser2){
        return friendService.getRelationUser1AndUser2(idUser1, idUser2);
    }

    @GetMapping("/getCountUserConsumptions/{id}")
    public int getCountUserConsumptions(@PathVariable String id){
        return userService.getAllUserConsumptions(id);
    }
    @GetMapping("/getAllUserServiceConsumption/{id}")
    public List<ServiceConsumptionDTO> getAllUserServiceConsumption(@PathVariable String id){
        return userService.getAllUserServiceConsumptions(id);
    }

    @PostMapping("/confirmPassword")
    public HashMap<String, Boolean> confirmPassword (@RequestParam String idUser,
                                                     @RequestParam String password) {
        HashMap<String, Boolean> answer = new HashMap<>();
        answer.put("authenticated", userService.confirmPassword(idUser, password));
        return answer;
    }
}
    //endregion

