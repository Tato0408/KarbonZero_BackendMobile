package api.karbonzeromovil.Services.Food;

import api.karbonzeromovil.Entities.Food.FoodCategoryEntity;
import api.karbonzeromovil.Models.DTO.Food.FoodCategoryDTO;
import api.karbonzeromovil.Repositories.Food.FoodCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FoodCategoryService {
    @Autowired
    FoodCategoryRepository repo;

    public List<FoodCategoryDTO> getAllCategories(){
        List<FoodCategoryEntity> category = repo.findAll();
        return category.stream()
                .map(this::categoryToDTO)
                .collect(Collectors.toList());
    }

    private FoodCategoryDTO categoryToDTO(FoodCategoryEntity category){
        FoodCategoryDTO dto = new FoodCategoryDTO();
        dto.setIdFoodCategory(category.getIdFoodCategory());
        dto.setFoodCategoryName(category.getFoodCategoryName());
        dto.setCarbonFootPrint(category.getCarbonFootPrint());
        return dto;
    }
}
