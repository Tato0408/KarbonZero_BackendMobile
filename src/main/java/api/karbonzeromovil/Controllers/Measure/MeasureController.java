package api.karbonzeromovil.Controllers.Measure;

import api.karbonzeromovil.Models.DTO.Measure.MeasureDTO;
import api.karbonzeromovil.Models.DTO.Measure.MeasureUnitDTO;
import api.karbonzeromovil.Services.Measure.MeasureService;
import api.karbonzeromovil.Services.Measure.MeasureUnitService;
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
@RequestMapping("/apiMeasure")
public class MeasureController {
    @Autowired
    private MeasureUnitService serviceMeasureUnit;
    @Autowired
    private MeasureService serviceMeasure;

    @GetMapping("/getAllMeasureUnits")
    public List<MeasureUnitDTO> dataMeasureUnit(){return serviceMeasureUnit.getAllMeasureUnit();}
    @GetMapping("/getAllMeasures")
    public List<MeasureDTO> dataMeasure(){return serviceMeasure.getAllMeasure();}

    @PostMapping("/insertMeasureUnit")
    public ResponseEntity<Map<String, Object>> insertMeasureUnit(@Valid @RequestBody MeasureUnitDTO dto, HttpServletRequest request){
        try{
            MeasureUnitDTO answer =  serviceMeasureUnit.insertMeasureUnit(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to create measure unit object");
            }
            return responseCreated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't create measure unit object");
        }
    }
    @PostMapping("/insertMeasure")
    public ResponseEntity<Map<String, Object>> insertMeasure (@Valid @RequestBody MeasureDTO dto, HttpServletRequest request){
        try{
            MeasureDTO answer =  serviceMeasure.insertMeasure(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to measure object");
            }
            return responseCreated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't create measure object");
        }
    }
    @PutMapping("/updateMeasureUnit/{id}")
    public ResponseEntity<?> updateMeasureUnit(@PathVariable String id, @Valid @RequestBody MeasureUnitDTO dto, BindingResult bindingresult){
        if (bindingresult.hasErrors()){
            return responseBadRequestBinding(bindingresult);
        }
        try{
            MeasureUnitDTO answer = serviceMeasureUnit.updateMeasureUnit(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update a measure unit object ");
            }
            return responseUpdated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't update a measure unit object");
        }
    }
    @PutMapping("/updateMeasure/{id}")
    public ResponseEntity<?> updateMeasure(@PathVariable String id, @Valid @RequestBody MeasureDTO dto, BindingResult bindingresult){
        if (bindingresult.hasErrors()){
            return responseBadRequestBinding(bindingresult);
        }
        try{
            MeasureDTO answer = serviceMeasure.updateMeasure(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update a measure object ");
            }
            return responseUpdated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't update a measure object");
        }
    }
    @DeleteMapping("/deleteMeasureUnit/{id}")
    public ResponseEntity<Map<String, Object>> deleteMeasureUnit(@PathVariable String id){
        try{
            if(!serviceMeasureUnit.deleteMeasureUnit(id)){
                return responseNotFound("Measure Unit not found");
            }
            return responseOK("Measure unit deleted with success");
        }
        catch(Exception e){
            return responseServerError(e,"Can't delete the measure unit type object");
        }
    }
    @DeleteMapping("/deleteMeasure/{id}")
    public ResponseEntity<Map<String, Object>> deleteMeasure(@PathVariable String id){
        try{
            if(!serviceMeasure.deleteMeasure(id)){
                return responseNotFound("Measure not found");
            }
            return responseOK("Measure deleted with success");
        }
        catch(Exception e){
            return responseServerError(e,"Can't delete the measure type object");
        }
    }
}

