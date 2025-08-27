package api.karbonzeromovil.Services.Car;

import api.karbonzeromovil.Entities.Car.FuelConsumptionEntity;
import api.karbonzeromovil.Entities.Car.TypeCarEntity;
import api.karbonzeromovil.Models.DTO.Car.FuelConsumptionDTO;
import api.karbonzeromovil.Repositories.Car.FuelConsumptionRepository;
import api.karbonzeromovil.Repositories.Car.TypeCarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service

public class FuelConsumptionService {

    @Autowired
    private FuelConsumptionRepository repo;

    @Autowired
    private TypeCarRepository TypeCarRepo;

    public List<FuelConsumptionDTO> getAllFuelConsumption(){
        List<FuelConsumptionEntity> fuelConsumption = repo.findAll();
        return fuelConsumption.stream()
                .map(this::FuelConsumptionToDTO)
                .collect(Collectors.toList());
    }

    public FuelConsumptionDTO getFuelConsumptionById (String id){
        if(repo.existsById(id)){
            FuelConsumptionEntity entity = repo.getReferenceById(id);
            return FuelConsumptionToDTO(entity);
        }
        return null;
    }

    public FuelConsumptionDTO insertFuelConsumption(FuelConsumptionDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Fuel Consumption size cannot be null or empty");
        }
        try {
            FuelConsumptionEntity entity = DT0ToFuelConsumption(dto);
            FuelConsumptionEntity savedFuelConsumption = repo.save(entity);
            return FuelConsumptionToDTO(savedFuelConsumption);
        } catch (Exception e) {
            throw new RuntimeException("The fuel consumption size could not be created"+ e.getMessage());
        }
    }

    public FuelConsumptionDTO updateFuelConsumption(String id, FuelConsumptionDTO dto){
        try {
            if (repo.existsById((id))){
                FuelConsumptionEntity entity = repo.getById(id);
                entity.setIdTypeCar(TypeCarRepo.getReferenceById(dto.getIdTypeCar()));
                entity.setQuantity(dto.getQuantity());
                entity.setConsumsuptionDate(dto.getConsumptionDate());
                entity.setLatitude(dto.getLatitude());
                entity.setLongitude(dto.getLongitude());

                FuelConsumptionEntity savedFuelConsumption = repo.save(entity);
                return FuelConsumptionToDTO(savedFuelConsumption);
            }
            throw new IllegalArgumentException("The fuel consumption could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The fuel consumption could not be updated" + e.getMessage());
        }
    }

    public boolean deleteFuelConsumption(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The fuel consumption with the ID : " + id + " doesn't exists", 1);
        }
    }

    public FuelConsumptionDTO FuelConsumptionToDTO(FuelConsumptionEntity entity){
        FuelConsumptionDTO dto = new FuelConsumptionDTO();
        dto.setIdFuelConsumption(entity.getIdFuelConsumption());
        dto.setIdTypeCar(entity.getIdTypeCar().getIdTypeCar());
        dto.setQuantity(entity.getQuantity());
        dto.setConsumptionDate(entity.getConsumsuptionDate());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        return dto;
    }
    private FuelConsumptionEntity DT0ToFuelConsumption(FuelConsumptionDTO dto){
        FuelConsumptionEntity entity = new FuelConsumptionEntity();
        entity.setIdFuelConsumption(dto.getIdFuelConsumption());
        entity.setIdTypeCar(TypeCarRepo.getReferenceById(dto.getIdTypeCar()));
        entity.setQuantity(dto.getQuantity());
        entity.setConsumsuptionDate(dto.getConsumptionDate());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        return entity;
    }
}
