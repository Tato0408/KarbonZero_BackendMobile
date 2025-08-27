package api.karbonzeromovil.Controllers.Company;

import api.karbonzeromovil.Models.DTO.Company.ChannelDTO;
import api.karbonzeromovil.Models.DTO.Company.ChannelUserDTO;
import api.karbonzeromovil.Models.DTO.Company.CompanyDTO;
import api.karbonzeromovil.Models.DTO.User.UserDTO;
import api.karbonzeromovil.Services.Company.ChannelService;
import api.karbonzeromovil.Services.Company.ChannelUserService;
import api.karbonzeromovil.Services.Company.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

@RestController
@RequestMapping("/apiCompany")

public class CompanyController {
    @Autowired
    private CompanyService serviceCompany;

    @Autowired
    private ChannelService serviceChannel;

    @Autowired
    private ChannelUserService serviceChannelUser;



    //Metodos Get para obtener cada cosa relacionada con la tabla company
    @GetMapping("/getAllCompany")
    public ResponseEntity<Page<CompanyDTO>> dataCompany(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if(size <= 0 || size > 50){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "El tamaño de la pagina debe ser entre 1 y 50"
            ));
            return ResponseEntity.ok(null);
        }
        Page<CompanyDTO> dto = serviceCompany.getAllCompanies(page, size);
        if(dto == null){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "No hay registros"
            ));
        }
        return ResponseEntity.ok(dto);
    }
  
    @GetMapping("/getAllCompaniesSP")
    public List<CompanyDTO> dataCompany(){return serviceCompany.getAllCompaniesSp();}

    @GetMapping("/getAllChannel")
    public List<ChannelDTO> dataChannel(){return serviceChannel.getAllChannel(); }

    @GetMapping("/getAllChannelUser")
    public List<ChannelUserDTO> dateChannelUser(){return serviceChannelUser.getAllChannelUser();}


    @GetMapping("/getCompanyById/{id}")
    public CompanyDTO getCompanyById(@PathVariable String id){
        return serviceCompany.getCompanyById(id);
    }

    @GetMapping("/getChannelById/{id}")
    public ChannelDTO getChannelById(@PathVariable String id){
        return serviceChannel.getChannelById(id);
    }

    @GetMapping("/getChannelUserById/{id}")
    public ChannelUserDTO getChannelUserById(@PathVariable String id){
        return serviceChannelUser.getChannelUserById(id);
    }

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
