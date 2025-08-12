package api.karbonzeromovil.Controllers.User;

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

import java.util.List;
import java.util.Map;

import static api.karbonzeromovil.Utils.Responses.*;

@RestController
@RequestMapping("/apiUser")
public class UserController {
    @Autowired
    private CountryService countryService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserAchievementService userAchievementService;
    @Autowired
    private UserService userService;


    @GetMapping("/getAllCountries")
    public List<CountryDTO> getAllCountries(){return  countryService.getAllCountries();}

    @GetMapping("/getAllFriends")
    public List<FriendDTO> getAllFriends(){return friendService.getAllFriends();}

    @GetMapping("/getAllAchievements")
    public ResponseEntity<Page<UserAchievementDTO>> getAllAchievements(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if(size <=0 || size>50){
            ResponseEntity.badRequest().body(Map.of("status","El tamanio de la pagina debe de estar entre 1 y 50"));
            return ResponseEntity.ok(null);
        }
        Page<UserAchievementDTO> achievements = userAchievementService.getAllAchievements(page, size);
        if(achievements ==null){
            ResponseEntity.badRequest().body(Map.of("status", "No hay logros registrados "));
        }
        return ResponseEntity.ok(achievements);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<Page<UserDTO>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if(size<=0 || size>50){
            ResponseEntity.badRequest().body(Map.of("status","El tamanio de la pagina debe de estar entre 1 y 50"));
            return ResponseEntity.ok(null);
        }
        Page<UserDTO> users = userService.getAllUsers(page, size);
        if(users == null){
            ResponseEntity.badRequest().body(Map.of("status", "No hay usuarios registrados "));
        }

        return ResponseEntity.ok(users);
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
}
