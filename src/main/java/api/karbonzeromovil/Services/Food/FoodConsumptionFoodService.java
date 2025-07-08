package api.karbonzeromovil.Services.Food;

import api.karbonzeromovil.Entities.Food.FoodConsumptionFoodEntity;
import api.karbonzeromovil.Models.DTO.Food.FoodConsumptionFoodDTO;
import api.karbonzeromovil.Repositories.Food.FoodConsumptionFoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FoodConsumptionFoodService {
    @Autowired
    private FoodConsumptionFoodRepository repo;

    public List<FoodConsumptionFoodDTO> getAllFoodConsumptionFood(){
        List<FoodConsumptionFoodEntity> entity = repo.findAll();
        return entity.stream()
                .map(this::foodConsumptionFoodToDTO)
                .collect(Collectors.toList());
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
                FoodConsumptionFoodEntity entity =  repo.getById(id);

                entity.setIdFood(dto.getIdFood());
                entity.setIdFoodConsumption(dto.getIdFoodConsumption());

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
        dto.setIdFood(entity.getIdFood());
        dto.setIdFoodConsumption(entity.getIdFoodConsumption());

        return dto;
    }

    private FoodConsumptionFoodEntity DTOToFoodConsumptionFood(FoodConsumptionFoodDTO dto){
        FoodConsumptionFoodEntity entity = new FoodConsumptionFoodEntity();

        entity.setIdFoodConsumptionsFood(dto.getIdFoodConsumptionsFood());
        entity.setIdFood(dto.getIdFood());
        entity.setIdFoodConsumption(dto.getIdFoodConsumption());

        return entity;
    }
}
