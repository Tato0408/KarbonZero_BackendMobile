package api.karbonzeromovil.Controllers.LivingSpace;

import api.karbonzeromovil.Models.DTO.LivingSpace.LivingSpaceTypeDTO;
import api.karbonzeromovil.Services.LivingSpace.LivingSpaceTypeService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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

    @GetMapping("/getLivingSpaceType")
    public List<LivingSpaceTypeDTO> dataLivingSpaceType(){return serviceLivingSpaceType.getAllLivingSpaceType();}

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
}