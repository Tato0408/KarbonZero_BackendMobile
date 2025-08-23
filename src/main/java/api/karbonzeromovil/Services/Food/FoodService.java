package api.karbonzeromovil.Services.Food;

import api.karbonzeromovil.Entities.Food.FoodCategoryEntity;
import api.karbonzeromovil.Entities.Food.FoodEntity;
import api.karbonzeromovil.Models.DTO.Food.FoodDTO;
import api.karbonzeromovil.Repositories.Food.FoodCategoryRepository;
import api.karbonzeromovil.Repositories.Food.FoodRepository;
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
public class FoodService {
    @Autowired
    private FoodRepository repo;
    @Autowired
     private FoodCategoryRepository repoCategory;

    public Page<FoodDTO> getAllFood(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<FoodEntity> entity = repo.findAll(pageable);

        return entity.map(this::foodToDTO);
    }

    public FoodDTO getFoodById(String id){
        if(repo.existsById(id)){
            FoodEntity entity = repo.getReferenceById(id);
            return foodToDTO(entity);
        }
        return null;
    }

    public FoodDTO insertFood(FoodDTO dto){
        if (dto == null){
            throw new IllegalArgumentException("Food can't be null or empty");
        }
        try{
            FoodEntity entity = DTOToFood(dto);
            FoodEntity savedEntity = repo.save(entity);
            return foodToDTO(savedEntity);
        } catch(Exception e){
            throw new RuntimeException("Can't create food" + e.getMessage());
        }
    }

    public FoodDTO updateFood(String id, FoodDTO dto){
        try{
            if (repo.existsById(id)) {
                FoodEntity entity = repo.getReferenceById(id);
                entity.setFoodName(dto.getFoodName());
                if(dto.getIdFoodCategory() != null){
                    FoodCategoryEntity entityCategory = repoCategory.findById(dto.getIdFoodCategory())
                            .orElseThrow(()-> new IllegalArgumentException("Category can't be find by the id "));
                    entity.setFoodCategory(entityCategory);
                   }else{
                    entity.setFoodCategory(null);
                }
                FoodEntity savedEntity = repo.save(entity);
                return foodToDTO(savedEntity);
            }
            throw new IllegalArgumentException("The food with de id: " + id + " doesn't exist");
        }catch (Exception e){
            throw new RuntimeException("Can't update food " + e.getMessage());
        }
    }

    public boolean deleteFood(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            } else {
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The food with de id: " + id + " doesn't exist", 1);
        }
    }


    private FoodDTO foodToDTO(FoodEntity food){
        FoodDTO dto = new FoodDTO();
        dto.setIdFood(food.getIdFood());
        dto.setFoodName(food.getFoodName());
        if(food.getFoodCategory() != null){
            dto.setCategoryName(food.getFoodCategory().getFoodCategoryName());
            dto.setIdFoodCategory(food.getFoodCategory().getIdFoodCategory());
            dto.setCarbonFootprint(food.getFoodCategory().getCarbonFootPrint());
        }
        else{
            dto.setCategoryName("The food hasn't a category asigned");
        }
        return dto;
    }

    private FoodEntity DTOToFood (FoodDTO dto){
        FoodEntity entity = new FoodEntity();
        entity.setIdFood(dto.getIdFood());
        entity.setFoodName(dto.getFoodName());
        if(dto.getIdFoodCategory()!= null){
            FoodCategoryEntity food = repoCategory.findById(dto.getIdFoodCategory())
                    .orElseThrow(() -> new IllegalArgumentException("The category with the ID: " + dto.getIdFoodCategory() + "doesn't existe"));
            entity.setFoodCategory(food);
        }
        return entity;
    }
}
