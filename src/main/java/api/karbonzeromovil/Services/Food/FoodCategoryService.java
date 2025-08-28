package api.karbonzeromovil.Services.Food;

import api.karbonzeromovil.Entities.Food.FoodCategoryEntity;
import api.karbonzeromovil.Entities.Food.FoodEntity;
import api.karbonzeromovil.Models.DTO.Food.FoodCategoryDTO;
import api.karbonzeromovil.Models.DTO.Food.FoodDTO;
import api.karbonzeromovil.Repositories.Food.FoodCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FoodCategoryService {
    @Autowired
    FoodCategoryRepository repo;


    public List<FoodCategoryDTO>getAllCategories(){
        List<FoodCategoryEntity> entity = repo.findAll();
        return entity.stream()
                .map(this::categoryToDTO)
                .collect(Collectors.toList());
    }
    public  Page<FoodCategoryDTO>getAllCategoriesPagination(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<FoodCategoryEntity> entity = repo.findAll(pageable);

        return entity.map(this::categoryToDTO);
    }

    public FoodCategoryDTO getFoodCategoryById(String id){
        if(repo.existsById(id)){
            FoodCategoryEntity entity = repo.getReferenceById(id);
            return categoryToDTO(entity);
        }
        return null;
    }

    public Page<FoodCategoryDTO> getCategoryByName(String foodCategoryName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FoodCategoryEntity> users = repo.findByFoodCategoryName(foodCategoryName, pageable);
        return users.map(this::categoryToDTO);
    }

    public FoodCategoryDTO insertCategory(FoodCategoryDTO dto){
        if (dto == null){
            throw new IllegalArgumentException("Food can't be null or empty");
        }
        try{
            FoodCategoryEntity entity = DTOToCategory(dto);
            FoodCategoryEntity savedEntity = repo.save(entity);
            return categoryToDTO(savedEntity);
        }catch(Exception e){
            throw new RuntimeException("Can't create food" + e.getMessage());
        }
    }

    public FoodCategoryDTO updateCategory(String id, FoodCategoryDTO dto){
        try{
            if(repo.existsById(id)){
                FoodCategoryEntity category = repo.getReferenceById(id);

                category.setFoodCategoryName(dto.getFoodCategoryName());
                category.setCarbonFootPrint(dto.getCarbonFootPrint());

                FoodCategoryEntity savedEntity =  repo.save(category);
                return categoryToDTO(savedEntity);
            }
            throw new IllegalArgumentException("The category with de id: "+ id + "doesn't exist");
        }catch(Exception e){
            throw new RuntimeException("Can't update category " + e.getMessage());
        }
    }

    public boolean deleteCategory(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch(EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The category with the id: " + id + " doesn't exist", 1);
        }
    }

    private FoodCategoryDTO categoryToDTO(FoodCategoryEntity category){
        FoodCategoryDTO dto = new FoodCategoryDTO();
        dto.setIdFoodCategory(category.getIdFoodCategory());
        dto.setFoodCategoryName(category.getFoodCategoryName());
        dto.setCarbonFootPrint(category.getCarbonFootPrint());
        return dto;
    }

    private FoodCategoryEntity DTOToCategory(FoodCategoryDTO dto){
        FoodCategoryEntity category = new FoodCategoryEntity();
        category.setIdFoodCategory(dto.getIdFoodCategory());
        category.setFoodCategoryName(dto.getFoodCategoryName());
        category.setCarbonFootPrint(dto.getCarbonFootPrint());
        return category;
    }
}