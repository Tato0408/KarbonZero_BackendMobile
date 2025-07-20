package api.karbonzeromovil.Controllers.Food;

import api.karbonzeromovil.Models.DTO.Food.FoodCategoryDTO;
import api.karbonzeromovil.Models.DTO.Food.FoodConsumptionDTO;
import api.karbonzeromovil.Models.DTO.Food.FoodConsumptionFoodDTO;
import api.karbonzeromovil.Models.DTO.Food.FoodDTO;
import api.karbonzeromovil.Services.Food.FoodCategoryService;
import api.karbonzeromovil.Services.Food.FoodConsumptionFoodService;
import api.karbonzeromovil.Services.Food.FoodConsumptionService;
import api.karbonzeromovil.Services.Food.FoodService;
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
@RequestMapping("/apiFood")
public class FoodController {

    @Autowired
    private FoodService service;
    @Autowired
    private FoodCategoryService serviceCategory;
    @Autowired
    private FoodConsumptionService serviceConsumption;
    @Autowired
    private FoodConsumptionFoodService serviceFoodConsumption;

    @GetMapping("/getAllFoods")
    public List<FoodDTO> getAllFoods(){return service.getAllFood();}

    @GetMapping("/getAllFoodCategories")
    public List<FoodCategoryDTO> getAllFoodCategories(){return serviceCategory.getAllCategories();}

    @GetMapping("/getAllFoodConsumptions")
    public List<FoodConsumptionDTO> getAllFoodConsumptions(){return serviceConsumption.getAllFoodConsumption(); }

    @GetMapping("/getAllFoodConsumptionFoods")
    public List<FoodConsumptionFoodDTO> getAllFoodConsumptionFoods(){return serviceFoodConsumption.getAllFoodConsumptionFoods(); }

    @PostMapping("/insertFood")
    public ResponseEntity<Map<String, Object >> insertFood(@Valid @RequestBody FoodDTO dto, HttpServletRequest request){
        try{
            FoodDTO answer = service.insertFood(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to create food object");
            }
            return responseCreated(answer);
        } catch (Exception e){
            return responseServerError(e, "Can't create food object");
        }
    }


    @PostMapping("/insertFoodCategory")
    public ResponseEntity<Map<String, Object>> insertCategoryFood(@Valid @RequestBody FoodCategoryDTO dto, HttpServletRequest request){
        try{
            FoodCategoryDTO answer = serviceCategory.insertCategory(dto);
            if(answer == null){
                return responseBadRequest("Invalid Data to create food category object");
            }
            return responseCreated(answer);
        }catch (Exception e){
            return responseServerError(e,"Can't create food object" );
        }

    }

    @PostMapping("/insertFoodConsumption")
    public ResponseEntity<Map<String, Object>> insertFoodConsumption(@Valid @RequestBody FoodConsumptionDTO dto, HttpServletRequest request){
        try{
            FoodConsumptionDTO answer = serviceConsumption.insertFooConsumption(dto);
            if(answer == null){
                return  responseBadRequest("Invalid Data to create a food consumption object");
            }
            return responseCreated(answer);
        } catch(Exception e){
            return responseServerError(e, "Can't create a food consumption object");
        }
    }

    @PostMapping("/insertFoodConsumptionFood")
    public ResponseEntity<Map<String, Object>> insertFoodConsumptionFood(@Valid @RequestBody FoodConsumptionFoodDTO dto, HttpServletRequest request){
        try{
            FoodConsumptionFoodDTO answer =  serviceFoodConsumption.insertFoodConsumptionFood(dto);
            if(answer == null){
                return responseBadRequest("Invalid data to create a food consumption food object");
            }
            return responseCreated(answer);
        }catch (Exception e){
            return responseServerError(e, "Can't create food consumption food object");
        }
    }


    @PutMapping("/updateFood/{id}")
    public ResponseEntity <?> updateFood(
            @PathVariable String id,
            @Valid @RequestBody FoodDTO dto,
            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return responseBadRequestBinding(bindingResult);
        }
        try{
            FoodDTO answer = service.updateFood(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update food object");
            }
            return responseUpdated(answer);
        } catch(Exception e){
            return responseServerError(e, "Can't update a food object");
        }
    }

    @PutMapping("/updateFoodCategory/{id}")
    public ResponseEntity<?>updateFoodCategory(@PathVariable String id, @Valid @RequestBody FoodCategoryDTO dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return responseBadRequestBinding(bindingResult);
        }
        try{
            FoodCategoryDTO answer = serviceCategory.updateCategory(id, dto);
            if(answer == null){
                return responseBadRequest("Invalid data to update a food object");
            }
            return responseUpdated(answer);
        }catch (Exception e){
            return responseServerError(e, "Can't update a food object");
        }
    }

    @PutMapping("/updateFoodConsumption/{id}")
    public ResponseEntity<?>updateFoodConsumption(@PathVariable String id, @Valid @RequestBody FoodConsumptionDTO dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return responseBadRequestBinding(bindingResult);
        }
        try{
            FoodConsumptionDTO answer = serviceConsumption.updateFoodConsumption(id, dto);
            if(answer == null){
               return responseBadRequest("Invalid data to update food consumption object");
            }
            return responseUpdated(answer);
        }catch(Exception e){
            return responseServerError(e, "Can't update a food consumption object");
        }
    }

    @PutMapping("/updateFoodConsumptionFood/{id}")
    public ResponseEntity <?> updateFoodConsumptionFood(@PathVariable String id, @Valid @RequestBody FoodConsumptionFoodDTO dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return responseBadRequestBinding(bindingResult);
        }
        try{
            FoodConsumptionFoodDTO answer = serviceFoodConsumption.updateFoodConsumptionFood(id, dto);
                if(answer == null){
                    return responseBadRequest("Invalid data to update a food consumption food object");
                }
                return responseUpdated(answer);
        }catch(Exception e){
            return responseServerError(e,"Can't update a food consumption food object");
        }
    }

    @DeleteMapping("/deleteFood/{id}")
    public ResponseEntity<Map<String, Object>> deleteFood(
            @PathVariable String id){
        try{
            if(!service.deleteFood(id)){
                return responseNotFound ("Food not found");
            }
            return responseOK("Food deleted with success");
        } catch(Exception e){
            return responseServerError(e, "Can't delete the food object");
        }
    }

    @DeleteMapping("/deleteFoodCategory/{id}")
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable String id){
        try{
            if(!serviceCategory.deleteCategory(id)){
                return responseNotFound("Category not found ");
            }
            return responseOK("Category deleted with success");
        } catch (Exception e){
            return responseServerError(e, "Can't delete the category object");
        }


        }
    @DeleteMapping("/deleteFoodConsumption/{id}")
    public ResponseEntity<Map<String, Object>> deleteFoodConsumption(@PathVariable String id){
        try{
            if(!serviceConsumption.deleteFoodConsumption(id)){
                return responseNotFound("Food consumption not found");
            }
            return  responseOK("Food consumption deleted with success");
        } catch(Exception e){
            return responseServerError(e, "Can't delete the food consumption object");
        }
    }

    @DeleteMapping("/deleteFoodConsumptionFood/{id}")
    public ResponseEntity<Map<String , Object>> deleteFoodConsumptionFood(@PathVariable String id){
        try{
            if(!serviceFoodConsumption.deleteFoodConsumptionFood(id)){
                return responseNotFound("Food consumption food not found ");
            }
            return responseOK("Food consumption food deleted with success");
        }catch(Exception e){
            return responseServerError(e, "Can't delete the food consumption food object");
        }
    }
}

