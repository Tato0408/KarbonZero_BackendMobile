package api.karbonzeromovil.Services.Car;

import api.karbonzeromovil.Entities.Car.FuelTypeEntity;
import api.karbonzeromovil.Models.DTO.Car.FuelTypeDTO;
import api.karbonzeromovil.Repositories.Car.FuelTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service

public class FuelTypeService {
    @Autowired
    private FuelTypeRepository repo;

    public List<FuelTypeDTO> getAllFuelType(){
        List<FuelTypeEntity> Fueltype = repo.findAll();
        return Fueltype.stream()
                .map(this::FuelTypeToDTO)
                .collect(Collectors.toList());
    }

    public FuelTypeDTO getFuelTypeById (String id) {
        if(repo.existsById(id)){
            FuelTypeEntity entity = repo.getReferenceById(id);
            return FuelTypeToDTO(entity);
        }
        return null;
    }

    public FuelTypeDTO insertFuelType(FuelTypeDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Fuel type cannot be null or empty");
        }
        try {
            FuelTypeEntity entity = DT0ToFuelType(dto);
            FuelTypeEntity savedTypeCar = repo.save(entity);
            return FuelTypeToDTO(savedTypeCar);
        } catch (Exception e) {
            throw new RuntimeException("The fuel type could not be created"+ e.getMessage());
        }
    }

    public FuelTypeDTO updateFuelType(String id, FuelTypeDTO dto){
        try {
            if (repo.existsById((id))){
                FuelTypeEntity entity = repo.getById(id);
                entity.setIdMeasureUnit(dto.getIdMeasureUnit());
                entity.setName(dto.getName());
                entity.setCarbonFooTprint(dto.getCarbonFootprint());

                FuelTypeEntity savedTypeFuel = repo.save(entity);
                return FuelTypeToDTO(savedTypeFuel);
            }
            throw new IllegalArgumentException("The fuel type could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The fuel type could not be updated" + e.getMessage());
        }
    }

    public boolean deleteFuelType(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The fuel type with the ID : " + id + " doesn't exists", 1);
        }
    }

    private FuelTypeDTO FuelTypeToDTO(FuelTypeEntity entity){
        FuelTypeDTO dto = new FuelTypeDTO();
        dto.setIdFuelType(entity.getIdFuelType());
        dto.setIdMeasureUnit(entity.getIdMeasureUnit());
        dto.setName(entity.getName());
        dto.setCarbonFootprint(entity.getCarbonFooTprint());
        return dto;
    }
    private FuelTypeEntity DT0ToFuelType(FuelTypeDTO dto){
        FuelTypeEntity entity = new FuelTypeEntity();
        entity.setIdFuelType(dto.getIdFuelType());
        entity.setIdMeasureUnit(dto.getIdMeasureUnit());
        entity.setName(dto.getName());
        entity.setCarbonFooTprint(dto.getCarbonFootprint());
        return entity;
    }
}
