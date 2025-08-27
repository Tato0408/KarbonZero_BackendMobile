package api.karbonzeromovil.Services.Food;

import api.karbonzeromovil.Entities.Food.FoodCategoryEntity;
import api.karbonzeromovil.Entities.Food.FoodConsumptionEntity;
import api.karbonzeromovil.Entities.Food.FoodConsumptionFoodEntity;
import api.karbonzeromovil.Entities.Food.FoodEntity;
import api.karbonzeromovil.Entities.User.UserEntity;
import api.karbonzeromovil.Models.DTO.Food.FoodCategoryDTO;
import api.karbonzeromovil.Models.DTO.Food.FoodConsumptionDTO;
import api.karbonzeromovil.Models.DTO.Food.FoodConsumptionFoodDTO;
import api.karbonzeromovil.Models.DTO.Food.FoodDTO;
import api.karbonzeromovil.Models.DTO.User.FriendDTO;
import api.karbonzeromovil.Repositories.Food.FoodCategoryRepository;
import api.karbonzeromovil.Repositories.Food.FoodConsumptionFoodRepository;
import api.karbonzeromovil.Repositories.Food.FoodConsumptionRepository;
import api.karbonzeromovil.Repositories.Food.FoodRepository;
import api.karbonzeromovil.Repositories.User.UserRepository;
import api.karbonzeromovil.Services.User.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FoodConsumptionService {
    @Autowired
    private FoodConsumptionRepository repo;
    @Autowired
    private UserRepository repoUser;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FoodCategoryRepository foodCategoryRepository;
    @Autowired
    private FoodConsumptionFoodRepository foodCfoodRepo;
    public Page<FoodConsumptionDTO> getAllFoodConsumption(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<FoodConsumptionEntity> entity = repo.findAll(pageable);

        return entity.map(this::foodConsumptionToDTO);
    }

    public FoodConsumptionDTO getFoodConsumptionById(String id){
        if(repo.existsById(id)){
            FoodConsumptionEntity entity = repo.getReferenceById(id);
            return foodConsumptionToDTO(entity);
        }
        return null;
    }

    public List<FoodConsumptionFoodDTO> getAllfoodConsumptionFoodByFoodConsumption(String id){
        FoodConsumptionEntity foodC = repo.getReferenceById(id);
        return foodC.getFoodConsumptionFood().stream()
                .map(new FoodConsumptionFoodService()::foodConsumptionFoodToDTO).collect(Collectors.toList());
    }
    public HashMap<String, Double> getAllFoodConsumptionsByUser(String idUser){
            HashMap<String, Double> hashMap = new HashMap<>();
            UserEntity entity = repoUser.getReferenceById(idUser);
            hashMap.put(entity.getIdUser(), 0.00);

            List<FoodConsumptionEntity> foodConsumptions = entity.getFoodConsumpionsUser();

            foodConsumptions.forEach(foodConsumption ->{

                FoodConsumptionEntity foodConsumptionEntity = repo.getReferenceById(foodConsumption.getIdFoodConsumption());

                List<FoodConsumptionFoodEntity> foodConsumptionsFood = foodConsumptionEntity.getFoodConsumptionFood();

                foodConsumptionsFood.forEach(foodConsumptionFood->{

                    FoodEntity foodEntity = foodRepository.getReferenceById(foodConsumptionFood.getFood().getIdFood());

                    List<FoodConsumptionFoodEntity> foods = foodEntity.getFoodConsumptionFood();

                    foods.forEach(foodsConsumptions->{

                        FoodCategoryEntity foodCategory = foodCategoryRepository.getReferenceById(foodsConsumptions.getFood().getFoodCategory().getIdFoodCategory());

                        List<FoodEntity> foodsList = foodCategory.getFoodCategoryList();

                        foodsList.forEach(foodCat ->{
                            hashMap.put(entity.getIdUser(), (foodCat.getFoodCategory().getCarbonFootPrint())+ hashMap.get(entity.getIdUser()));
                        });
                    });
                });
            });
        return hashMap;
    }

    public FoodConsumptionDTO insertFooConsumption(FoodConsumptionDTO dto){
        if(dto == null){
            throw new IllegalArgumentException("Food consumption can't be null or empty");
        }
        try{
            FoodConsumptionEntity entity =  DTOToFoodConsumption(dto);
            FoodConsumptionEntity savedEntity = repo.save(entity);
            return foodConsumptionToDTO(savedEntity);
        }catch(Exception e){
            throw new RuntimeException("Can't create food consumption " + e.getMessage());
        }
    }

    public FoodConsumptionDTO updateFoodConsumption(String id, FoodConsumptionDTO dto){
        try{
            if(repo.existsById(id)){
                FoodConsumptionEntity entity = repo.getReferenceById(id);

                entity.setConsumptionDate(dto.getConsumptionDate());
                entity.setLatitude(dto.getLatitude());
                entity.setLongitude(dto.getLongitude());
                if(dto.getIdUser() != null){
                    UserEntity user = repoUser.findById(dto.getIdUser())
                            .orElseThrow(()-> new IllegalArgumentException("User with the id not found"));
                    entity.setIdUser(user);
                }

                FoodConsumptionEntity savedEntity = repo.save(entity);

                return foodConsumptionToDTO((savedEntity));
            }
            throw new IllegalArgumentException("The food consumption with the id: " + id + "doesn't exist");
        }catch (Exception e){
            throw new RuntimeException("Can't update food consumption " +  e.getMessage());
        }
    }

    public boolean deleteFoodConsumption(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else {
                return false;
            }
        }
        catch(EmptyResultDataAccessException  e){
            throw new EmptyResultDataAccessException("The food consumption with the id: " + id + "doesn't exist" ,1);
         }
    }

    public FoodConsumptionDTO foodConsumptionToDTO(FoodConsumptionEntity consumption){
        FoodConsumptionDTO dto = new FoodConsumptionDTO();

        dto.setIdFoodConsumption(consumption.getIdFoodConsumption());
        dto.setConsumptionDate(consumption.getConsumptionDate());
        dto.setLatitude(consumption.getLatitude());
        dto.setLongitude(consumption.getLongitude());
        if(consumption.getIdUser()!= null){
            dto.setUserName(consumption.getIdUser().getUserName());
            dto.setIdUser(consumption.getIdUser().getIdUser());
        }

        return dto;
    }

    public FoodConsumptionEntity DTOToFoodConsumption(FoodConsumptionDTO dto){
        FoodConsumptionEntity entity = new FoodConsumptionEntity();

        entity.setIdFoodConsumption(dto.getIdFoodConsumption());
        entity.setConsumptionDate(dto.getConsumptionDate());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        if(dto.getIdUser() != null){
            UserEntity user = repoUser.findById(dto.getIdUser())
                    .orElseThrow(()-> new IllegalArgumentException("User with the id not found"));
            entity.setIdUser(user);
        }
        return entity;
    }
}
