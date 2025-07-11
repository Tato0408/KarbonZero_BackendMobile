package api.karbonzeromovil.Controllers.Service;

import api.karbonzeromovil.Models.DTO.Service.ServiceDTO;
import api.karbonzeromovil.Services.Service.ServiceService;
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
@RequestMapping("/apiService")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @GetMapping("/getAllService")
    public List<ServiceDTO> getAllService(){
        return serviceService.getAllService();
    }

    @PostMapping("/insertService")
    public ResponseEntity<Map<String,Object>> insertTransport(@Valid @RequestBody ServiceDTO dto, HttpServletRequest request){
        try {
            ServiceDTO answer = serviceService.insertService(dto);
            if (answer == null){
                return responseBadRequest("Invalid data to create a service object");
            }
            return responseCreated(answer);
        } catch (Exception e) {
            return responseServerError(e, "Cannot create service object");
        }
    }
    @PutMapping("/updateService/{id}")
    public ResponseEntity<?>updateService(@PathVariable String id, @Valid @RequestBody ServiceDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return responseBadRequestBinding(bindingResult);
        }
        try {
            ServiceDTO answer = serviceService.updateService(id,dto);
            if (answer== null){
                return responseBadRequest("Invalid data to update a transport object");
            }
            return responseUpdated(answer);
        }catch (Exception e){
            return responseServerError(e, "Cannot update a transport object");
        }
    }
    @DeleteMapping("/deleteService/{id}")
    public ResponseEntity<Map<String, Object>> deleteTransport(@PathVariable String id) {
        try {
            if(!serviceService.deleteService(id)){
                return responseNotFound("Transport not found");
            }
            return responseOK("Transport deleted with success");
        } catch (Exception e) {
            return responseServerError(e, "Cannot delete the transport with the country Object");
        }
    }
}
