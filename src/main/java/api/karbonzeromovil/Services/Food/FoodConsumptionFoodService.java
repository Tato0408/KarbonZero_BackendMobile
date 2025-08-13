package api.karbonzeromovil.Services.Food;

import api.karbonzeromovil.Entities.Food.FoodConsumptionEntity;
import api.karbonzeromovil.Entities.Food.FoodConsumptionFoodEntity;
import api.karbonzeromovil.Entities.Food.FoodEntity;
import api.karbonzeromovil.Models.DTO.Food.FoodConsumptionFoodDTO;
import api.karbonzeromovil.Models.DTO.Food.FoodDTO;
import api.karbonzeromovil.Repositories.Food.FoodConsumptionFoodRepository;
import api.karbonzeromovil.Repositories.Food.FoodConsumptionRepository;
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
public class FoodConsumptionFoodService {
    @Autowired
    private FoodConsumptionFoodRepository repo;
    @Autowired
    private FoodRepository repoFood;
    @Autowired
    private FoodConsumptionRepository repoConsumption;

    public Page<FoodConsumptionFoodDTO> getAllFoodConsumptionFoods(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<FoodConsumptionFoodEntity> entity = repo.findAll(pageable);

        return entity.map(this::foodConsumptionFoodToDTO);
    }

    public FoodConsumptionFoodDTO getFoodConsumptionFoodById(String id){
        if(repo.existsById(id)){
            FoodConsumptionFoodEntity entity = repo.getReferenceById(id);
            return foodConsumptionFoodToDTO(entity);
        }
        return null;
    }

    public FoodConsumptionFoodDTO insertFoodConsumptionFood(FoodConsumptionFoodDTO dto){
        if(dto == null){
            throw new IllegalArgumentException("Food consumption food can't be null or empty");
        }
        try{
            FoodConsumptionFoodEntity entity =  DTOToFoodConsumptionFood(dto);

            FoodConsumptionFoodEntity savedEntity =  repo.save(entity);
            return foodConsumptionFoodToDTO(savedEntity);
        }catch(Exception e){
            throw new RuntimeException("Can't create food consumption food" + e.getMessage());
        }
    }

    public FoodConsumptionFoodDTO updateFoodConsumptionFood(String id, FoodConsumptionFoodDTO dto){
        try{
            if(repo.existsById(id)){
                FoodConsumptionFoodEntity entity =  repo.getReferenceById(id);
                if(dto.getIdFoodConsumption() != null && dto.getIdFood()!= null){
                    FoodConsumptionEntity consumption = repoConsumption.findById(dto.getIdFoodConsumption())
                            .orElseThrow(()-> new IllegalArgumentException("Can't find the id food consumption "));
                    FoodEntity food = repoFood.findById(dto.getIdFood())
                            .orElseThrow(()-> new IllegalArgumentException("Can't find the id food"));
                    entity.setFoodConsumption(consumption);
                    entity.setFood(food);
                }
                FoodConsumptionFoodEntity savedEntity = repo.save(entity);
                return foodConsumptionFoodToDTO(savedEntity);
            }
            throw new IllegalArgumentException("The food consumption food with the id " + id + " doesn't exist" );
        }catch(Exception e){
            throw new RuntimeException("Can't update food consumption food" + e.getMessage());
        }
    }

    public boolean deleteFoodConsumptionFood(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }
            else{
                return false;
            }
        }
        catch(EmptyResultDataAccessException e ){
            throw new EmptyResultDataAccessException("The food consumption food with the id: " + id + "doesn't exist", 1);
        }

    }


    private FoodConsumptionFoodDTO foodConsumptionFoodToDTO(FoodConsumptionFoodEntity entity){
        FoodConsumptionFoodDTO dto = new FoodConsumptionFoodDTO();

        dto.setIdFoodConsumptionsFood(entity.getIdFoodConsumptionsFood());
       if(entity.getFoodConsumption()!= null && entity.getFood()!= null){
           dto.setIdFoodConsumption(entity.getFoodConsumption().getIdFoodConsumption());
           dto.setFoodName(entity.getFood().getFoodName());
           dto.setIdFood(entity.getFood().getIdFood());
       }
        return dto;
    }

    private FoodConsumptionFoodEntity DTOToFoodConsumptionFood(FoodConsumptionFoodDTO dto){
        FoodConsumptionFoodEntity entity = new FoodConsumptionFoodEntity();

        entity.setIdFoodConsumptionsFood(dto.getIdFoodConsumptionsFood());
        if(dto.getIdFoodConsumption() != null && dto.getIdFood()!= null){
            FoodConsumptionEntity consumption = repoConsumption.findById(dto.getIdFoodConsumption())
                    .orElseThrow(()-> new IllegalArgumentException("Can't find the id food consumption "));
            FoodEntity food = repoFood.findById(dto.getIdFood())
                    .orElseThrow(()-> new IllegalArgumentException("Can't find the id food"));
            entity.setFoodConsumption(consumption);
            entity.setFood(food);
        }

        return entity;
    }
}
