package api.karbonzeromovil.Controllers.Transport;

import api.karbonzeromovil.Models.DTO.Transport.TransportConsumptionDTO;
import api.karbonzeromovil.Models.DTO.Transport.TransportDTO;
import api.karbonzeromovil.Services.Transport.TransportConsumptionService;
import api.karbonzeromovil.Services.Transport.TransportService;
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
@RequestMapping("/apiTransport")
public class TransportController {
    @Autowired
    private TransportService transportService;
    @Autowired
    private TransportConsumptionService transportConsumptionService;

    @GetMapping("/getAllTransports")
    public ResponseEntity<Page<TransportDTO>> getAllTransports(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if(size<=0 || size>50){
            ResponseEntity.badRequest().body(Map.of("status","El tamaño de la pagina debe de estar entre 1 y 50"));
            return ResponseEntity.ok(null);
        }
        Page<TransportDTO> transports = transportService.getAllTransports(page, size);
        if(transports == null){
            ResponseEntity.badRequest().body(Map.of("status", "No hay usuarios registrados "));
        }

        return ResponseEntity.ok(transports);
    }
    //@GetMapping("/getTransportConsumption")
    //public List<TransportConsumptionDTO> getAllTransportConsumption(){
      //  return transportConsumptionService.getAllTransportConsumption();
    //}
    @GetMapping("/getTransportConsumption")
    public ResponseEntity<Page<TransportConsumptionDTO>> getAllTransportConsumption(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if(size<=0 || size>50){
            ResponseEntity.badRequest().body(Map.of("status","El tamaño de la pagina debe de estar entre 1 y 50"));
            return ResponseEntity.ok(null);
        }
        Page<TransportConsumptionDTO> consumption = transportConsumptionService.getAllTransportConsumption(page, size);
        if(consumption == null){
            ResponseEntity.badRequest().body(Map.of("status", "No hay usuarios registrados "));
        }

        return ResponseEntity.ok(consumption);
    }
    @PostMapping("/insertTransport")
    public ResponseEntity<Map<String,Object>> insertTransport(@Valid @RequestBody TransportDTO dto, HttpServletRequest request){
        try {
            TransportDTO answer = transportService.insertTransport(dto);
            if (answer == null){
                return responseBadRequest("Invalid data to create a transport object");
            }
            return responseCreated(answer);
        } catch (Exception e) {
            return responseServerError(e, "Cannot create transport object");
        }
    }
    @PostMapping("/insertTransportConsumption")
    public ResponseEntity<Map<String,Object>> insertTransportConsumption(@Valid @RequestBody TransportConsumptionDTO dto, HttpServletRequest request){
        try {
            TransportConsumptionDTO answer = transportConsumptionService.insertTransportConsumption(dto);
            if (answer == null){
                return responseBadRequest("Invalid data to create an Transport Consumption object");
            }
            return responseCreated(answer);
        } catch (Exception e) {
            return responseServerError(e, "Cannot create Transport Consumption object");
        }
    }
    @PutMapping("/updateTransport/{id}")
    public ResponseEntity<?>updateTransport(@PathVariable String id, @Valid @RequestBody TransportDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return responseBadRequestBinding(bindingResult);
        }
        try {
            TransportDTO answer = transportService.updateTransport(id,dto);
            if (answer== null){
                return responseBadRequest("Invalid data to update a transport object");
            }
            return responseUpdated(answer);
        }catch (Exception e){
            return responseServerError(e, "Cannot update a transport object");
        }
    }
    @PutMapping("/updateTransportConsumption/{id}")
    public ResponseEntity<?>updateTransportConsumption(@PathVariable String id, @Valid @RequestBody TransportConsumptionDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return responseBadRequestBinding(bindingResult);
        }
        try {
            TransportConsumptionDTO answer = transportConsumptionService.updateTransportConsumption(id,dto);
            if (answer== null){
                return responseBadRequest("Invalid data to update an Transport Consumption object");
            }
            return responseUpdated(answer);
        }catch (Exception e){
            return responseServerError(e, "Cannot update an Transport Consumption object");
        }
    }
    @DeleteMapping("/deleteTransport/{id}")
    public ResponseEntity<Map<String, Object>> deleteTransport(@PathVariable String id) {
        try {
            if(!transportService.deleteTransport(id)){
                return responseNotFound("Transport not found");
            }
            return responseOK("Transport deleted with success");
        } catch (Exception e) {
            return responseServerError(e, "Cannot delete the transport with the country Object");
        }
    }
    @DeleteMapping("/deleteTransportConsumption/{id}")
    public ResponseEntity<Map<String, Object>> deleteTransportConsumption(@PathVariable String id) {
        try {
            if(!transportConsumptionService.deleteTransportConsumption(id)){
                return responseNotFound("Transport Consumption not found");
            }
            return responseOK("Transport Consumption deleted with success");
        } catch (Exception e) {
            return responseServerError(e, "Cannot delete the Transport Consumption with the Transport Consumption Object");
        }
    }
}
