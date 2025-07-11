package api.karbonzeromovil.Controllers.Company;

import api.karbonzeromovil.Models.DTO.Company.ChannelDTO;
import api.karbonzeromovil.Models.DTO.Company.ChannelUserDTO;
import api.karbonzeromovil.Models.DTO.Company.CompanyDTO;
import api.karbonzeromovil.Services.Company.ChannelService;
import api.karbonzeromovil.Services.Company.ChannelUserService;
import api.karbonzeromovil.Services.Company.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static api.karbonzeromovil.Utils.Responses.responseBadRequest;
import static api.karbonzeromovil.Utils.Responses.responseBadRequestBinding;
import static api.karbonzeromovil.Utils.Responses.responseCreated;
import static api.karbonzeromovil.Utils.Responses.responseNotFound;
import static api.karbonzeromovil.Utils.Responses.responseOK;
import static api.karbonzeromovil.Utils.Responses.responseServerError;
import static api.karbonzeromovil.Utils.Responses.responseUpdated;

public class CompanyController {
    @Autowired
    private CompanyService serviceCompany;

    @Autowired
    private ChannelService serviceChannel;

    @Autowired
    private ChannelUserService serviceChannelUser;



    //Metodos Get para obtener cada cosa relacionada con la tabla company
    @GetMapping("/getAllCompany")
    public List<CompanyDTO> dataCompany(){return serviceCompany.getAllCompany();}

    @GetMapping("/getAllChannel")
    public List<ChannelDTO> dataChannel(){return serviceChannel.getAllChannel(); }

    @GetMapping("/getAllChannelUser")
    public List<ChannelUserDTO> dateChannelUser(){return serviceChannelUser.getAllChannelUser();}




    //Metodos POST para insertar cada cosa relacionada con la tabla carro
    @PostMapping("/insertCompany")
    public ResponseEntity<Map<String, Object>> insertCompany(@Valid @RequestBody CompanyDTO dto, HttpServletRequest request){
        try{
            CompanyDTO answer =  serviceCompany.insertCompany(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to create car type object");
            }
            return responseCreated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't create car type object");
        }
    }

    @PostMapping("/insertChannel")
    public ResponseEntity<Map<String, Object>> insertChannel(@Valid @RequestBody ChannelDTO dto, HttpServletRequest request){
        try{
            ChannelDTO answer =  serviceChannel.insertChannel(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to size car space object");
            }
            return responseCreated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't create size car object");
        }
    }

    @PostMapping("/insertChannelUser")
    public ResponseEntity<Map<String, Object>> insertChannelUser(@Valid @RequestBody ChannelUserDTO dto, HttpServletRequest request){
        try{
            ChannelUserDTO answer =  serviceChannelUser.insertChannelUser(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to create type car type object");
            }
            return responseCreated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't create type car type object");
        }
    }




    //Metodos PUT para actualizar cada cosa relacionada con la tabla carro
    @PutMapping("/updateCompany/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable String id, @Valid @RequestBody CompanyDTO dto, BindingResult bindingresult){
        if (bindingresult.hasErrors()){
            return responseBadRequestBinding(bindingresult);
        }
        try{
            CompanyDTO answer = serviceCompany.updateCompany(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update a car object ");
            }
            return responseUpdated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't update a car object");
        }
    }

    @PutMapping("/updateChannel/{id}")
    public ResponseEntity<?> updateChannel(@PathVariable String id, @Valid @RequestBody ChannelDTO dto, BindingResult bindingresult){
        if (bindingresult.hasErrors()){
            return responseBadRequestBinding(bindingresult);
        }
        try{
            ChannelDTO answer = serviceChannel.updateChannel(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update a size car object ");
            }
            return responseUpdated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't update a size car object");
        }
    }

    @PutMapping("/updateChannelUser/{id}")
    public ResponseEntity<?> updateChannelUser(@PathVariable String id, @Valid @RequestBody ChannelUserDTO dto, BindingResult bindingresult){
        if (bindingresult.hasErrors()){
            return responseBadRequestBinding(bindingresult);
        }
        try{
            ChannelUserDTO answer = serviceChannelUser.updateChannelUser(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update a type car object ");
            }
            return responseUpdated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't update a type car object");
        }
    }



    //Metodos DELETE para borrar cada cosa relacionada con la tabla carro
    @DeleteMapping("/deleteCompany/{id}")
    public ResponseEntity<Map<String, Object>> deleteCar(@PathVariable String id){
        try{
            if(!serviceCompany.deleteCompany(id)){
                return responseNotFound("Car not found");
            }
            return responseOK("Car deleted with success");
        }
        catch(Exception e){
            return responseServerError(e,"Can't delete the car object");
        }
    }

    @DeleteMapping("/deleteChannel/{id}")
    public ResponseEntity<Map<String, Object>> deleteCarSize(@PathVariable String id){
        try{
            if(!serviceChannel.deleteChannel(id)){
                return responseNotFound("Car size not found");
            }
            return responseOK("Car size deleted with success");
        }
        catch(Exception e){
            return responseServerError(e,"Can't delete the car size object");
        }
    }

    @DeleteMapping("/deleteChannelUser/{id}")
    public ResponseEntity<Map<String, Object>> deleteTypeCar(@PathVariable String id){
        try{
            if(!serviceChannelUser.deleteChannelUser(id)){
                return responseNotFound("Type car not found");
            }
            return responseOK("Type car deleted with success");
        }
        catch(Exception e){
            return responseServerError(e,"Can't delete the type car object");
        }
    }
}
