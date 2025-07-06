package api.karbonzeromovil.Controllers.User;

import api.karbonzeromovil.Models.DTO.User.CountryDTO;
import api.karbonzeromovil.Models.DTO.User.FriendDTO;
import api.karbonzeromovil.Services.User.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
