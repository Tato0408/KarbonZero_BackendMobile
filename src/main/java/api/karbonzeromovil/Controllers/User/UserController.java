package api.karbonzeromovil.Controllers.User;

import api.karbonzeromovil.Models.DTO.User.CountryDTO;
import api.karbonzeromovil.Models.DTO.User.FriendDTO;
import api.karbonzeromovil.Services.User.CountryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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

    @GetMapping("/getAllCountries")
    public List<CountryDTO> getAllCountries(){
        return countryService.getAllCountries();
    }
    @PostMapping("/insertCountry")
    public ResponseEntity<Map<String,Object>> insertUser(@Valid @RequestBody CountryDTO dto, HttpServletRequest request){
        try {
            CountryDTO answer = countryService.insertCountry(dto);
            if (answer == null){
                return responseBadRequest("Invalida data to create a country object");
            }
            return responseCreated(answer);
        } catch (Exception e) {
            return responseServerError(e, "Cannor create country object");
        }
    }
    @PutMapping("/updateCountry/{id}")
    public ResponseEntity<?>updateRol(@PathVariable String id, @Valid @RequestBody CountryDTO dto, BindingResult bindingResult){
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
            return responseOK("User deleted with success");
        } catch (Exception e) {
            return responseServerError(e, "Cannot delete the country with the User Object");
        }
    }
}
