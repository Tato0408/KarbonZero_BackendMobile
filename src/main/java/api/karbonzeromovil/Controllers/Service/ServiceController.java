package api.karbonzeromovil.Controllers.Service;

import api.karbonzeromovil.Models.DTO.Service.MeasureUnitServiceDTO;
import api.karbonzeromovil.Models.DTO.Service.ServiceDTO;
import api.karbonzeromovil.Services.Service.MeasureUnitServiceService;
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
    @Autowired
    private MeasureUnitServiceService measureUnitServiceService;

    @GetMapping("/getAllServices")
    public List<ServiceDTO> getAllServices(){
        return serviceService.getAllServices();
    }
    @GetMapping("/getMeasureUnitService")
    public List<MeasureUnitServiceDTO> getAllMeasureUnitService(){return measureUnitServiceService.getAllMeasureUnitService();}
    @PostMapping("/insertService")
    public ResponseEntity<Map<String,Object>> insertService(@Valid @RequestBody ServiceDTO dto, HttpServletRequest request){
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
    @PostMapping("/insertMeasureUnitService")
    public ResponseEntity<Map<String,Object>> insertMeasureUnitService(@Valid @RequestBody MeasureUnitServiceDTO dto, HttpServletRequest request){
        try {
            MeasureUnitServiceDTO answer = measureUnitServiceService.insertMeasureUnitService(dto);
            if (answer == null){
                return responseBadRequest("Invalid data to create an Measure Unit Service object");
            }
            return responseCreated(answer);
        } catch (Exception e) {
            return responseServerError(e, "Cannot create Measure Unit Service object");
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
                return responseBadRequest("Invalid data to update a service object");
            }
            return responseUpdated(answer);
        }catch (Exception e){
            return responseServerError(e, "Cannot update a service object");
        }
    }
    @PutMapping("/updateMeasureUnitService/{id}")
    public ResponseEntity<?>updateMeasureUnitService(@PathVariable String id, @Valid @RequestBody MeasureUnitServiceDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return responseBadRequestBinding(bindingResult);
        }
        try {
            MeasureUnitServiceDTO answer = measureUnitServiceService.updateMeasureUnitService(id,dto);
            if (answer== null){
                return responseBadRequest("Invalid data to update an Measure Unit Service object");
            }
            return responseUpdated(answer);
        }catch (Exception e){
            return responseServerError(e, "Cannot update an Measure Unit Service object");
        }
    }
    @DeleteMapping("/deleteService/{id}")
    public ResponseEntity<Map<String, Object>> deleteService(@PathVariable String id) {
        try {
            if(!serviceService.deleteService(id)){
                return responseNotFound("Service not found");
            }
            return responseOK("Service deleted with success");
        } catch (Exception e) {
            return responseServerError(e, "Cannot delete the service with the service Object");
        }
    }
    @DeleteMapping("/deleteMeasureUnitService/{id}")
    public ResponseEntity<Map<String, Object>> deleteMeasureUnitService(@PathVariable String id) {
        try {
            if(!measureUnitServiceService.deleteMeasureUnitService(id)){
                return responseNotFound("Measure Unit Service not found");
            }
            return responseOK("Measure Unit Service deleted with success");
        } catch (Exception e) {
            return responseServerError(e, "Cannot delete the Measure Unit Service with the Measure Unit Service Object");
        }
    }
}
