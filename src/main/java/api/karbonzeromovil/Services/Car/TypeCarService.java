package api.karbonzeromovil.Services.Car;

import api.karbonzeromovil.Entities.Car.TypeCarEntity;
import api.karbonzeromovil.Models.DTO.Car.TypeCarDTO;
import api.karbonzeromovil.Repositories.Car.TypeCarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service

public class TypeCarService {
    @Autowired
    private TypeCarRepository repo;

    public List<TypeCarDTO> getAllTypeCar(){
        List<TypeCarEntity> cartype = repo.findAll();
        return cartype.stream()
                .map(this::TypeCarToDTO)
                .collect(Collectors.toList());
    }

    public TypeCarDTO insertTypeCar(TypeCarDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Car type cannot be null or empty");
        }
        try {
            TypeCarEntity entity = DT0ToTypeCar(dto);
            TypeCarEntity savedTypeCar = repo.save(entity);
            return TypeCarToDTO(savedTypeCar);
        } catch (Exception e) {
            throw new RuntimeException("The car type could not be created"+ e.getMessage());
        }
    }

    public TypeCarDTO updateTypeCar(String id, TypeCarDTO dto){
        try {
            if (repo.existsById((id))){
                TypeCarEntity entity = repo.getById(id);
                entity.setIdCar(dto.getIdCar());
                entity.setIdFuelType(dto.getIdFuelType());

                TypeCarEntity savedTypeCar = repo.save(entity);
                return TypeCarToDTO(savedTypeCar);
            }
            throw new IllegalArgumentException("The car type could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The car type could not be updated" + e.getMessage());
        }
    }

    public boolean deleteTypeCar(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The Car type with the ID : " + id + " doesn't exists", 1);
        }
    }

    private TypeCarDTO TypeCarToDTO(TypeCarEntity entity){
        TypeCarDTO dto = new TypeCarDTO();
        dto.setIdTypeCar(entity.getIdTypeCar());
        dto.setIdCar(entity.getIdCar());
        dto.setIdFuelType(entity.getIdFuelType());
        return dto;
    }
    private TypeCarEntity DT0ToTypeCar(TypeCarDTO dto){
        TypeCarEntity entity = new TypeCarEntity();
        entity.setIdTypeCar(entity.getIdTypeCar());
        entity.setIdCar(dto.getIdCar());
        entity.setIdFuelType(dto.getIdFuelType());
        return entity;
    }
}
