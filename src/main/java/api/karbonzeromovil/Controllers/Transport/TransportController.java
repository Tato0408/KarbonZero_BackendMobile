package api.karbonzeromovil.Controllers.Transport;

import api.karbonzeromovil.Models.DTO.Transport.TransportDTO;
import api.karbonzeromovil.Services.Transport.TransportService;
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
@RequestMapping("/apiTransport")
public class TransportController {
    @Autowired
    private TransportService transportService;

    @GetMapping("/getAllTransport")
    public List<TransportDTO> getAllTransport(){
        return transportService.getAllTransport();
    }

    @PostMapping("/insertTransport")
    public ResponseEntity<Map<String,Object>> insertTransport(@Valid @RequestBody TransportDTO dto, HttpServletRequest request){
        try {
            TransportDTO answer = transportService.insertTransport(dto);
            if (answer == null){
                return responseBadRequest("Invalid data to create a country object");
            }
            return responseCreated(answer);
        } catch (Exception e) {
            return responseServerError(e, "Cannot create country object");
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
                return responseBadRequest("Invalid data to update a country object");
            }
            return responseUpdated(answer);
        }catch (Exception e){
            return responseServerError(e, "Cannot update a country object");
        }
    }
    @DeleteMapping("/deleteTransport/{id}")
    public ResponseEntity<Map<String, Object>> deleteTransport(@PathVariable String id) {
        try {
            if(!transportService.deleteTransport(id)){
                return responseNotFound("Country not found");
            }
            return responseOK("Country deleted with success");
        } catch (Exception e) {
            return responseServerError(e, "Cannot delete the country with the country Object");
        }
    }
}
