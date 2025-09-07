package api.karbonzeromovil.Controllers.Pet;

import api.karbonzeromovil.Models.DTO.Food.FoodDTO;
import api.karbonzeromovil.Models.DTO.Pet.PetTypeDTO;
import api.karbonzeromovil.Models.DTO.Pet.UserPetDTO;
import api.karbonzeromovil.Services.Pet.PetTypeService;
import api.karbonzeromovil.Services.Pet.UserPetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import static api.karbonzeromovil.Utils.Responses.*;
import static api.karbonzeromovil.Utils.Responses.responseBadRequest;
import static api.karbonzeromovil.Utils.Responses.responseCreated;
import static api.karbonzeromovil.Utils.Responses.responseServerError;

@RestController
@RequestMapping("/apiPet")
@CrossOrigin(origins = "*")
public class PetController {
    @Autowired
    private PetTypeService servicePetType;
    @Autowired
    private UserPetService serviceUserPet;

    @GetMapping("/getAllUserPets")
    public ResponseEntity<Page<UserPetDTO>> getAllUserPets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        if(size <= 0 || size > 50){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "El tamaño de la página debe de ser de 1 a 50"
            ));
        }
        Page<UserPetDTO> dto = serviceUserPet.getAllUserPets (page, size);
        if(dto == null){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "No hay registros"
            ));
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getAllPetType")
    public ResponseEntity<Page<PetTypeDTO>> getAllPetType(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        if(size <= 0 || size > 50){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "El tamaño de la página debe de ser de 1 a 50"
            ));
        }
        Page<PetTypeDTO> dto = servicePetType.getAllPetType (page, size);
        if(dto == null){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "No hay registros"
            ));
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getUserPetById/{id}")
    public UserPetDTO getUserPetById(@PathVariable String id) {return serviceUserPet.getUserPetById(id);}

    @GetMapping("/getPetTypeById/{id}")
    public PetTypeDTO getPetTypeById(@PathVariable String id) {return servicePetType.getPetTypeById(id);}

    @GetMapping("/getPetTypeByName/{namePetType}")
    public Page<PetTypeDTO> getPetTypeByName (@PathVariable String namePetType,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        if (size <= 0 || size > 50) {
            return null;
        }
        return servicePetType.getPetTypeByName(namePetType, page, size);
    }


    @PostMapping("/insertUserPet")
    public ResponseEntity<Map<String, Object>> insertUserPet(@Valid @RequestBody UserPetDTO dto, HttpServletRequest request){
        try{
            UserPetDTO answer =  serviceUserPet.insertUserPet(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to create pet user object");
            }
            return responseCreated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't create pet user object");
        }
    }
    @PostMapping("/insertPetType")
    public ResponseEntity<Map<String, Object>> insertPetType (@Valid @RequestBody PetTypeDTO dto, HttpServletRequest request){
        try{
            PetTypeDTO answer =  servicePetType.insertPetType(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to pet type object");
            }
            return responseCreated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't create pet type object");
        }
    }
    @PutMapping("/updateUserPet/{id}")
    public ResponseEntity<?> updateUserPet(@PathVariable String id, @Valid @RequestBody UserPetDTO dto, BindingResult bindingresult){
        if (bindingresult.hasErrors()){
            return responseBadRequestBinding(bindingresult);
        }
        try{
            UserPetDTO answer = serviceUserPet.updateUserPet(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update a user pet object ");
            }
            return responseUpdated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't update a user pet object");
        }
    }
    @PutMapping("/updatePetType/{id}")
    public ResponseEntity<?> updatePetType(@PathVariable String id, @Valid @RequestBody PetTypeDTO dto, BindingResult bindingresult){
        if (bindingresult.hasErrors()){
            return responseBadRequestBinding(bindingresult);
        }
        try{
            PetTypeDTO answer = servicePetType.updatePetType(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update a pet type object ");
            }
            return responseUpdated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't update a pet type object");
        }
    }
    @DeleteMapping("/deleteUserPet/{id}")
    public ResponseEntity<Map<String, Object>> deleteUserPet(@PathVariable String id){
        try{
            if(!serviceUserPet.deleteUserPet(id)){
                return responseNotFound("User Pet not found");
            }
            return responseOK("User pet deleted with success");
        }
        catch(Exception e){
            return responseServerError(e,"Can't delete the user pet object");
        }
    }
    @DeleteMapping("/deletePetType/{id}")
    public ResponseEntity<Map<String, Object>> deletePetType(@PathVariable String id){
        try{
            if(!servicePetType.deletePetType(id)){
                return responseNotFound("Pet type not found");
            }
            return responseOK("Pet type deleted with success");
        }
        catch(Exception e){
            return responseServerError(e,"Can't delete the pet type object");
        }
    }
}
