package api.karbonzeromovil.Services.Food;

import api.karbonzeromovil.Entities.Food.FoodEntity;
import api.karbonzeromovil.Models.DTO.Food.FoodDTO;
import api.karbonzeromovil.Repositories.Food.FoodCategoryRepository;
import api.karbonzeromovil.Repositories.Food.FoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public List<FoodDTO> getAllFood(){
        List<FoodEntity> food = repo.findAll();
        return food.stream()
                .map(this::foodToDTO)
                .collect(Collectors.toList());
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
                FoodEntity entity = repo.getById(id);

                entity.setFoodName(dto.getFoodName());
                entity.setIdFoodCategory(repoCategory.getReferenceById(dto.getIdFoodCategory()));
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
        dto.setIdFoodCategory(food.getIdFoodCategory().getIdFoodCategory());
        dto.setFoodName(food.getFoodName());
        return dto;
    }

    private FoodEntity DTOToFood (FoodDTO dto){
        FoodEntity entity = new FoodEntity();
        entity.setIdFood(dto.getIdFood());
        entity.setIdFoodCategory(repoCategory.getReferenceById(dto.getIdFoodCategory()));
        entity.setFoodName(dto.getFoodName());
        return entity;
    }
}
