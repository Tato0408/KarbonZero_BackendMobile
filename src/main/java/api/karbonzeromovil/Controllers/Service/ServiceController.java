package api.karbonzeromovil.Controllers.Service;

import api.karbonzeromovil.Models.DTO.Service.MeasureUnitServiceDTO;
import api.karbonzeromovil.Models.DTO.Service.ServiceConsumptionDTO;
import api.karbonzeromovil.Models.DTO.Service.ServiceDTO;
import api.karbonzeromovil.Services.Service.MeasureUnitServiceService;
import api.karbonzeromovil.Services.Service.ServiceConsumptionService;
import api.karbonzeromovil.Services.Service.ServiceService;
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
@RequestMapping("/apiService")
@CrossOrigin
public class ServiceController {
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private MeasureUnitServiceService measureUnitServiceService;
    @Autowired
    private ServiceConsumptionService serviceConsumptionService;

    @GetMapping("/getAllServices")
    public ResponseEntity<Page<ServiceDTO>> getAllServices(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if(size<=0 || size>50){
            ResponseEntity.badRequest().body(Map.of("status","El tamaño de la pagina debe de estar entre 1 y 50"));
            return ResponseEntity.ok(null);
        }
        Page<ServiceDTO> service = serviceService.getAllServices(page, size);
        if(service == null){
            ResponseEntity.badRequest().body(Map.of("status", "No hay usuarios registrados "));
        }
        return ResponseEntity.ok(service);
    }
    @GetMapping("/getMeasureUnitService")
    public ResponseEntity<Page<MeasureUnitServiceDTO>> getAllMeasureUnitService(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if(size<=0 || size>50){
            ResponseEntity.badRequest().body(Map.of("status","El tamaño de la pagina debe de estar entre 1 y 50"));
            return ResponseEntity.ok(null);
        }
        Page<MeasureUnitServiceDTO> measureUnit = measureUnitServiceService.getAllMeasureUnitService(page, size);
        if(measureUnit == null){
            ResponseEntity.badRequest().body(Map.of("status", "No hay usuarios registrados "));
        }

        return ResponseEntity.ok(measureUnit);
    }
    @GetMapping("/getServiceConsumption")
    public ResponseEntity<Page<ServiceConsumptionDTO>> getAllServiceConsumption(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if(size<=0 || size>50){
            ResponseEntity.badRequest().body(Map.of("status","El tamaño de la pagina debe de estar entre 1 y 50"));
            return ResponseEntity.ok(null);
        }
        Page<ServiceConsumptionDTO> consumption = serviceConsumptionService.getAllServiceConsumption(page, size);
        if(consumption == null){
            ResponseEntity.badRequest().body(Map.of("status", "No hay usuarios registrados "));
        }

        return ResponseEntity.ok(consumption);
    }

    @GetMapping("/getAllServiceList")
    public List<ServiceDTO> getServiceNP() {return serviceService.getAllServiceNP();}

    @GetMapping("/getServiceById/{id}")
    public ServiceDTO getServiceById(@PathVariable String id){

        return serviceService.getServiceById(id);
    }
    @GetMapping("/getMeasureUnitServiceById/{id}")
    public MeasureUnitServiceDTO getMeasureServiceById(@PathVariable String id){
        return measureUnitServiceService.getMeasureUnitServiceById(id);
    }
    @GetMapping("/getServiceConsumptionById/{id}")
    public ServiceConsumptionDTO geServiceConsumptionById(@PathVariable String id){
        return serviceConsumptionService.getServiceConsumptionById(id);
    }
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
    @PostMapping("/insertServiceConsumption")
    public ResponseEntity<Map<String,Object>> insertServiceConsumption(@Valid @RequestBody ServiceConsumptionDTO dto, HttpServletRequest request){
        try {
            ServiceConsumptionDTO answer = serviceConsumptionService.insertServiceConsumption(dto);
            if (answer == null){
                return responseBadRequest("Invalid data to create an Service Consumption object");
            }
            return responseCreated(answer);
        } catch (Exception e) {
            return responseServerError(e, "Cannot create Service Consumption object");
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
    @PutMapping("/updateServiceConsumption/{id}")
    public ResponseEntity<?>updateServiceConsumption(@PathVariable String id, @Valid @RequestBody ServiceConsumptionDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return responseBadRequestBinding(bindingResult);
        }
        try {
            ServiceConsumptionDTO answer = serviceConsumptionService.updateServiceConsumption(id,dto);
            if (answer== null){
                return responseBadRequest("Invalid data to update an Service Consumption object");
            }
            return responseUpdated(answer);
        }catch (Exception e){
            return responseServerError(e, "Cannot update an Service Consumption object");
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
    @DeleteMapping("/deleteServiceConsumption/{id}")
    public ResponseEntity<Map<String, Object>> deleteServiceConsumption(@PathVariable String id) {
        try {
            if(!serviceConsumptionService.deleteServiceConsumption(id)){
                return responseNotFound("Service Consumption not found");
            }
            return responseOK("Service Consumption deleted with success");
        } catch (Exception e) {
            return responseServerError(e, "Cannot delete the Service Consumption with the Service Consumption Object");
        }
    }
}
