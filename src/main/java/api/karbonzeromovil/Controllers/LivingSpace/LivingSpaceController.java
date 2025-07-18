package api.karbonzeromovil.Controllers.LivingSpace;

import api.karbonzeromovil.Models.DTO.LivingSpace.LivingSpaceDTO;
import api.karbonzeromovil.Models.DTO.LivingSpace.LivingSpaceTypeDTO;
import api.karbonzeromovil.Services.LivingSpace.LivingSpaceService;
import api.karbonzeromovil.Services.LivingSpace.LivingSpaceTypeService;
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
@RequestMapping("/apiLivingSpace")
public class LivingSpaceController {
    @Autowired
    private LivingSpaceTypeService serviceLivingSpaceType;

    @Autowired
    private LivingSpaceService serviceLivingSpace;

    @GetMapping("/getAllLivingSpaceTypes")
    public List<LivingSpaceTypeDTO> getAllLivingSpaceTypes(){return serviceLivingSpaceType.getAllLivingSpaceType();}

    @GetMapping("/getAllLivingSpaces")
    public List<LivingSpaceDTO> getAllLivingSpaces(){return serviceLivingSpace.getAllLivingSpace(); }

    @PostMapping("/insertLivingSpaceType")
    public ResponseEntity<Map<String, Object>> insertLivingSpaceType(@Valid @RequestBody LivingSpaceTypeDTO dto, HttpServletRequest request){
        try{
            LivingSpaceTypeDTO answer =  serviceLivingSpaceType.insertLivingSpaceType(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to create living space type object");
            }
            return responseCreated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't create living space type object");
        }
    }

    @PostMapping("/insertLivingSpace")
    public ResponseEntity<Map<String, Object>> insertLivingSpace(@Valid @RequestBody LivingSpaceDTO dto, HttpServletRequest request){
        try{
            LivingSpaceDTO answer =  serviceLivingSpace.insertLivingSpace(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to create living space object");
            }
            return responseCreated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't create living space object");
        }
    }

    @PutMapping("/updateLivingSpaceType/{id}")
    public ResponseEntity<?> updateLivingSpaceType(@PathVariable String id, @Valid @RequestBody LivingSpaceTypeDTO dto, BindingResult bindingresult){
        if (bindingresult.hasErrors()){
           return responseBadRequestBinding(bindingresult);
        }
        try{
            LivingSpaceTypeDTO answer = serviceLivingSpaceType.updateLivingSpaceType(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update a living space type object ");
            }
            return responseUpdated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't update a living space type object");
        }
    }

    @PutMapping("/updateLivingSpace/{id}")
    public ResponseEntity<?> updateLivingSpace(@PathVariable String id, @Valid @RequestBody LivingSpaceDTO dto, BindingResult bindingresult){
        if (bindingresult.hasErrors()){
            return responseBadRequestBinding(bindingresult);
        }
        try{
            LivingSpaceDTO answer = serviceLivingSpace.updateLivingSpace(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update a living space object ");
            }
            return responseUpdated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't update a living space object");
        }
    }

    @DeleteMapping("/deleteLivingSpaceType/{id}")
    public ResponseEntity<Map<String, Object>> deleteLivingSpaceType(@PathVariable String id){
        try{
            if(!serviceLivingSpaceType.deleteLivingSpaceType(id)){
                return responseNotFound("Living space type not found");
            }
            return responseOK("Living space type deleted with success");
        }
        catch(Exception e){
            return responseServerError(e,"Can't delete the living space type object");
        }
    }

    @DeleteMapping("/deleteLivingSpace/{id}")
    public ResponseEntity<Map<String, Object>> deleteLivingSpace(@PathVariable String id){
        try{
            if(!serviceLivingSpace.deleteLivingSpace(id)){
                return responseNotFound("Living space not found");
            }
            return responseOK("Living space deleted with success");
        }
        catch(Exception e){
            return responseServerError(e,"Can't delete the living space object");
        }
    }
}