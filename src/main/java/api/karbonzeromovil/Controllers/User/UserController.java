package api.karbonzeromovil.Controllers.User;

import api.karbonzeromovil.Models.DTO.User.CountryDTO;
import api.karbonzeromovil.Models.DTO.User.FriendDTO;
import api.karbonzeromovil.Models.DTO.User.UserAchivmentDTO;
import api.karbonzeromovil.Models.DTO.User.UserDTO;
import api.karbonzeromovil.Services.User.CountryService;
import api.karbonzeromovil.Services.User.FriendService;
import api.karbonzeromovil.Services.User.UserAchivmentService;
import api.karbonzeromovil.Services.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserAchivmentService userAchivmentService;
    @Autowired
    private UserService userService;

    @GetMapping("/getAllCountries")
    public List<CountryDTO> getAllCountries(){
        return countryService.getAllCountries();
    }

    @GetMapping("/getAllFriends")
    public List<FriendDTO> getAllFriends(){return friendService.getAllFriends();}

    @GetMapping("/getAllAchivments")
    public List<UserAchivmentDTO> getAllAchivments(){
        return userAchivmentService.getAllAchivments();
    }

    @GetMapping("/getAllUsers")
    public List<UserDTO> getAllUsers(){return userService.getAllUsers();}

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
    public ResponseEntity<Map<String,Object>> isertFriend(@Valid @RequestBody FriendDTO dto, HttpServletRequest request){
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
    @PostMapping("/insertAchivment")
    public ResponseEntity<Map<String,Object>> insertAchivment(@Valid @RequestBody UserAchivmentDTO dto, HttpServletRequest request){
        try {
            UserAchivmentDTO answer = userAchivmentService.insertAchivment(dto);
            if (answer == null){
                return responseBadRequest("Invalid data to create an achivment object");
            }
            return responseCreated(answer);
        } catch (Exception e) {
            return responseServerError(e, "Cannot create achivment object");
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
    @PutMapping("/updateAchivment/{id}")
    public ResponseEntity<?>updateAchivment(@PathVariable String id, @Valid @RequestBody UserAchivmentDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return responseBadRequestBinding(bindingResult);
        }
        try {
            UserAchivmentDTO answer = userAchivmentService.updateAchivment(id,dto);
            if (answer== null){
                return responseBadRequest("Invalid data to update an achivment object");
            }
            return responseUpdated(answer);
        }catch (Exception e){
            return responseServerError(e, "Cannot update an achivment object");
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
    @DeleteMapping("/deleteAchivment/{id}")
    public ResponseEntity<Map<String, Object>> deleteAchivment(@PathVariable String id) {
        try {
            if(!userAchivmentService.deleteAchivment(id)){
                return responseNotFound("Achivment not found");
            }
            return responseOK("Achivment deleted with success");
        } catch (Exception e) {
            return responseServerError(e, "Cannot delete the Achivment with the achivment Object");
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
