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

@RestController
@RequestMapping("/apiFoods")
public class FoodController {

    @Autowired
    private FoodCategoryService serviceCategory;



    @GetMapping("/getFoodCategory")
    public List<FoodCategoryDTO> dataFoodCategory(){return serviceCategory.getAllCategories();}

}
