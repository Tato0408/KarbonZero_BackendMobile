package api.karbonzeromovil.Controllers.Pet;

import api.karbonzeromovil.Models.DTO.Pet.PetTypeDTO;
import api.karbonzeromovil.Models.DTO.Pet.UserPetDTO;
import api.karbonzeromovil.Services.Pet.PetTypeService;
import api.karbonzeromovil.Services.Pet.UserPetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static api.karbonzeromovil.Utils.Responses.*;
import static api.karbonzeromovil.Utils.Responses.responseBadRequest;
import static api.karbonzeromovil.Utils.Responses.responseCreated;
import static api.karbonzeromovil.Utils.Responses.responseServerError;

@RestController
@RequestMapping("api/Pet")
public class PetController {
    @Autowired
    private PetTypeService servicePetType;
    @Autowired
    private UserPetService serviceUserPet;
    @GetMapping("/getAllUserPet")
    public List<UserPetDTO> dataUserPet(){return serviceUserPet.getAllUserPet();}
    @GetMapping("/getAllPetType")
    public List<PetTypeDTO> dataPetType(){return servicePetType.getAllPetType();}

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
