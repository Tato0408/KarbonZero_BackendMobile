package api.karbonzeromovil.Controllers.Car;

import api.karbonzeromovil.Models.DTO.Car.CarDTO;
import api.karbonzeromovil.Models.DTO.Car.CarSizeDTO;
import api.karbonzeromovil.Models.DTO.Car.TypeCarDTO;
import api.karbonzeromovil.Models.DTO.Car.FuelTypeDTO;
import api.karbonzeromovil.Models.DTO.Car.FuelConsumptionDTO;

import api.karbonzeromovil.Models.DTO.Company.CompanyDTO;
import api.karbonzeromovil.Models.DTO.User.UserAchievementDTO;
import api.karbonzeromovil.Services.Car.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static api.karbonzeromovil.Utils.Responses.*;

@RestController
@RequestMapping("/apiCar")
@CrossOrigin
public class CarController {
    @Autowired
    private CarService serviceCar;

    @Autowired
    private CarSizeService serviceSizeCar;

    @Autowired
    private TypeCarService serviceTypeCar;

    @Autowired
    private FuelTypeService serviceFuelType;

    @Autowired
    private FuelConsumptionService serviceFuelConsumption;

    @GetMapping("/getFuelTypeByName/{name}")
    public Page<FuelTypeDTO> getFuelTypeByName (@PathVariable String name, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if(size <= 0 || size > 50){
            return null;
        }
        return serviceFuelType.getFuelTypeByName(name, page, size);
    }

    @GetMapping("/getCarSizeByName/{name}")
    public Page<CarSizeDTO> getCarSizeByName (@PathVariable String name, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if(size <= 0 || size > 50){
            return null;
        }
        return serviceSizeCar.getCarSizeByName(name, page, size);
    }


    //Metodos Get para obtener cada cosa relacionada con la tabla carro
    @GetMapping("/getAllCar")
    public List<CarDTO> dataCar(){return serviceCar.getAllCar();}

    @GetMapping("/getAllCarSize")
    public ResponseEntity<Page<CarSizeDTO>> dataCarSize(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if(size <= 0 || size > 50){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "El tamaño de la pagina debe ser entre 1 y 50"
            ));
            return ResponseEntity.ok(null);
        }
        Page<CarSizeDTO> dto = serviceSizeCar.getAllCarSize(page, size);
        if(dto == null){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "No hay registros"
            ));
        }
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/getCarSizeSp")
    public List<CarSizeDTO> getAllCarSizeSP(){
        return serviceSizeCar.getAllCarSizeSP();
    }
    @GetMapping("/getAllTypeCar")
    public List<TypeCarDTO> dateTypeCar(){return serviceTypeCar.getAllTypeCar();}

    @GetMapping("/getAllFuelType")
    public ResponseEntity<Page<FuelTypeDTO>> dataCompany(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        if(size <= 0 || size > 50){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "El tamaño de la pagina debe ser entre 1 y 50"
            ));
            return ResponseEntity.ok(null);
        }
        Page<FuelTypeDTO> dto = serviceFuelType.getAllFuelType(page, size);
        if(dto == null){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "No hay registros"
            ));
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getAllFuelTypeSP")
    public List<FuelTypeDTO> dataCompanySP(){
        return serviceFuelType.getAllFuelTypeSP();
    }
    @GetMapping("/getAllFuelConsumption")
    public List<FuelConsumptionDTO> dataFuelConsumption(){return serviceFuelConsumption.getAllFuelConsumption();}


    @GetMapping("/getCarById/{id}")
    public CarDTO getCarById(@PathVariable String id){
        return serviceCar.getCarById(id);
    }

    @GetMapping("getCarSizeById/{id}")
    public CarSizeDTO getCarSizeById(@PathVariable String id){
        return serviceSizeCar.getCarSizeById(id);
    }

    @GetMapping("/getTypeCarById/{id}")
    public TypeCarDTO getTypeCarById(@PathVariable String id){
        return serviceTypeCar.getTypeCarById(id);
    }

    @GetMapping("/getFuelTypeById/{id}")
    public FuelTypeDTO getFuelTypeById(@PathVariable String id){
        return serviceFuelType.getFuelTypeById(id);
    }

    @GetMapping("/getFuelConsumptionById/{id}")
    public FuelConsumptionDTO getFuelConsumptionById(@PathVariable String id){
        return serviceFuelConsumption.getFuelConsumptionById(id);
    }

    @GetMapping("/getFuelConsumptionsCO2WithLocation")
    public List<HashMap<?, ?>> getFuelConsumptionsCO2WithLocation (
            @RequestParam Double minX,
            @RequestParam Double maxX,
            @RequestParam Double minY,
            @RequestParam Double maxY
    ) {
        return serviceFuelConsumption.getFuelConsumptionsCO2WithLocation(minX, maxX, minY, maxY);
    }

    //Metodos POST para insertar cada cosa relacionada con la tabla carro
    @PostMapping("/insertCar")
    public ResponseEntity<Map<String, Object>> insertCar(@Valid @RequestBody CarDTO dto, HttpServletRequest request){
        try{
            CarDTO answer =  serviceCar.insertCar(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to create car type object");
            }
            return responseCreated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't create car type object");
        }
    }

    @PostMapping("/insertCarSize")
    public ResponseEntity<Map<String, Object>> insertCarSize(@Valid @RequestBody CarSizeDTO dto, HttpServletRequest request){
        try{
            CarSizeDTO answer =  serviceSizeCar.insertCarSize(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to size car space object");
            }
            return responseCreated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't create size car object");
        }
    }

    @PostMapping("/insertTypeCar")
    public ResponseEntity<Map<String, Object>> insertTypeCar(@Valid @RequestBody TypeCarDTO dto, HttpServletRequest request){
        try{
            TypeCarDTO answer =  serviceTypeCar.insertTypeCar(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to create type car type object");
            }
            return responseCreated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't create type car type object");
        }
    }

    @PostMapping("/insertFuelType")
    public ResponseEntity<Map<String, Object>> insertFuelType(@Valid @RequestBody FuelTypeDTO dto, HttpServletRequest request){
        try{
            FuelTypeDTO answer =  serviceFuelType.insertFuelType(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to create fuel type object");
            }
            return responseCreated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't create fuel type object");
        }
    }

    @PostMapping("/insertFuelConsumption")
    public ResponseEntity<Map<String, Object>> insertFuelConsumption(@Valid @RequestBody FuelConsumptionDTO dto, HttpServletRequest request){
        try{
            FuelConsumptionDTO answer =  serviceFuelConsumption.insertFuelConsumption(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to create fuel consumption type object");
            }
            return responseCreated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't create fuel consumption type object");
        }
    }




    //Metodos PUT para actualizar cada cosa relacionada con la tabla carro
    @PutMapping("/updateCar/{id}")
    public ResponseEntity<?> updateCar(@PathVariable String id, @Valid @RequestBody CarDTO dto, BindingResult bindingresult){
        if (bindingresult.hasErrors()){
            return responseBadRequestBinding(bindingresult);
        }
        try{
            CarDTO answer = serviceCar.updateCar(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update a car object ");
            }
            return responseUpdated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't update a car object");
        }
    }

    @PutMapping("/updateCarSize/{id}")
    public ResponseEntity<?> updateSizeCar(@PathVariable String id, @Valid @RequestBody CarSizeDTO dto, BindingResult bindingresult){
        if (bindingresult.hasErrors()){
            return responseBadRequestBinding(bindingresult);
        }
        try{
            CarSizeDTO answer = serviceSizeCar.updateSizeCar(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update a size car object ");
            }
            return responseUpdated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't update a size car object");
        }
    }

    @PutMapping("/updateTypeCar/{id}")
    public ResponseEntity<?> updateTypeCar(@PathVariable String id, @Valid @RequestBody TypeCarDTO dto, BindingResult bindingresult){
        if (bindingresult.hasErrors()){
            return responseBadRequestBinding(bindingresult);
        }
        try{
            TypeCarDTO answer = serviceTypeCar.updateTypeCar(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update a type car object ");
            }
            return responseUpdated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't update a type car object");
        }
    }

    @PutMapping("/updateFuelType/{id}")
    public ResponseEntity<?> updateFuelType(@PathVariable String id, @Valid @RequestBody FuelTypeDTO dto, BindingResult bindingresult){
        if (bindingresult.hasErrors()){
            return responseBadRequestBinding(bindingresult);
        }
        try{
            FuelTypeDTO answer = serviceFuelType.updateFuelType(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update a fuel type object ");
            }
            return responseUpdated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't update a fuel type object");
        }
    }

    @PutMapping("/updateFuelConsumption/{id}")
    public ResponseEntity<?> updateFuelConsumption(@PathVariable String id, @Valid @RequestBody FuelConsumptionDTO dto, BindingResult bindingresult){
        if (bindingresult.hasErrors()){
            return responseBadRequestBinding(bindingresult);
        }
        try{
            FuelConsumptionDTO answer = serviceFuelConsumption.updateFuelConsumption(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update a fuel consumption object ");
            }
            return responseUpdated(answer);
        }
        catch(Exception e){
            return responseServerError(e,"Can't update a fuel consumption object");
        }
    }



    //Metodos DELETE para borrar cada cosa relacionada con la tabla carro
    @DeleteMapping("/deleteCar/{id}")
    public ResponseEntity<Map<String, Object>> deleteCar(@PathVariable String id){
        try{
            if(!serviceCar.deleteCar(id)){
                return responseNotFound("Car not found");
            }
            return responseOK("Car deleted with success");
        }
        catch(Exception e){
            return responseServerError(e,"Can't delete the car object");
        }
    }

    @DeleteMapping("/deleteCarSize/{id}")
    public ResponseEntity<Map<String, Object>> deleteCarSize(@PathVariable String id){
        try{
            if(!serviceSizeCar.deleteCarSize(id)){
                return responseNotFound("Car size not found");
            }
            return responseOK("Car size deleted with success");
        }
        catch(Exception e){
            return responseServerError(e,"Can't delete the car size object");
        }
    }

    @DeleteMapping("/deleteTypeCar/{id}")
    public ResponseEntity<Map<String, Object>> deleteTypeCar(@PathVariable String id){
        try{
            if(!serviceTypeCar.deleteTypeCar(id)){
                return responseNotFound("Type car not found");
            }
            return responseOK("Type car deleted with success");
        }
        catch(Exception e){
            return responseServerError(e,"Can't delete the type car object");
        }
    }

    @DeleteMapping("/deleteFuelType/{id}")
    public ResponseEntity<Map<String, Object>> deleteFuelType(@PathVariable String id){
        try{
            if(!serviceFuelType.deleteFuelType(id)){
                return responseNotFound("Fuel type not found");
            }
            return responseOK("Fuel type deleted with success");
        }
        catch(Exception e){
            return responseServerError(e,"Can't delete the fuel type object");
        }
    }

    @DeleteMapping("/deleteFuelConsumption/{id}")
    public ResponseEntity<Map<String, Object>> deleteFuelConsumption(@PathVariable String id){
        try{
            if(!serviceFuelConsumption.deleteFuelConsumption(id)){
                return responseNotFound("Fuel Consumption not found");
            }
            return responseOK("Fuel Consumption deleted with success");
        }
        catch(Exception e){
            return responseServerError(e,"Can't delete the fuel consumption object");
        }
    }

    @GetMapping("/getAllTypeCarsByCar/{id}")
    public List<TypeCarDTO> getAllTypeCarsByCar(@PathVariable String id){
        return serviceCar.getAllTypeCarsByCar(id);
    }
    @GetMapping("/getAllFuelConsumptionsByTypeCar/{id}")
    public List<FuelConsumptionDTO> getAllFuelConsumptionsByTypeCar(@PathVariable String id){
        return serviceTypeCar.getAllFuelConsumptionsByTypeCar(id);
    }

    @GetMapping("/getAllC02CarsByUser/{id}")
    public HashMap<String, Double> getAllCarsC02ByUserCar(@PathVariable String id){

        return serviceTypeCar.getAllCarsC02ByUserCar(id);
    }

}
