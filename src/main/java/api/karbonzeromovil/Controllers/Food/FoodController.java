package api.karbonzeromovil.Controllers.Food;

import api.karbonzeromovil.Entities.Food.FoodCategoryEntity;
import api.karbonzeromovil.Models.DTO.Food.FoodCategoryDTO;
import api.karbonzeromovil.Models.DTO.Food.FoodDTO;
import api.karbonzeromovil.Services.Food.FoodCategoryService;
import api.karbonzeromovil.Services.Food.FoodService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static api.karbonzeromovil.Utils.Responses.*;

@RestController
@RequestMapping("/apiFoods")
public class FoodController {

    @Autowired
    private FoodCategoryService serviceCategory;

    @GetMapping("/getFoodCategory")
    public List<FoodCategoryDTO> dataFoodCategory(){return serviceCategory.getAllCategories();}

    @PostMapping("/insertFoodCategory")
    public ResponseEntity<Map<String, Object>> insertCategoryFood(@Valid @RequestBody FoodCategoryDTO dto, HttpServletRequest request){
        try{
            FoodCategoryDTO answer = serviceCategory.insertCategory(dto);
            if(answer == null){
                return responseBadRequest("Invalid Data to create food object");
            }
            return responseCreated(answer);
        }catch (Exception e){
            return responseServerError(e,"Can't create food object" );
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

    @DeleteMapping("deleteCategory/{id}")
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable String id){
        try{
            if(!serviceCategory.deleteCategory(id)){
                return responseNotFound("Category not found ");
            }
            return responseOK("Category deleted with succes");
        } catch (Exception e){
            return responseServerError(e, "Can't delete the category object");
        }
    }
}
