package api.karbonzeromovil.Services.Car;

import api.karbonzeromovil.Entities.Car.CarEntity;
import api.karbonzeromovil.Models.DTO.Car.CarDTO;
import api.karbonzeromovil.Repositories.Car.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service

public class CarService {

    @Autowired
    private CarRepository repo;

    public List<CarDTO> getAllCar(){
        List<CarEntity> cars = repo.findAll();
        return cars.stream()
                .map(this::CarsToDTO)
                .collect(Collectors.toList());
    }

    public CarDTO getCarById(String id){
        if(repo.existsById(id)){
            CarEntity entity = repo.getReferenceById(id);
            return CarsToDTO(entity);
        }
        return null;
    }

    public CarDTO insertCar(CarDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Car cannot be null or empty");
        }
        try {
            CarEntity entity = DT0ToCars(dto);
            CarEntity savedCar = repo.save(entity);
            return CarsToDTO(savedCar);
        } catch (Exception e) {
            throw new RuntimeException("The car could not be created"+ e.getMessage());
        }
    }

    public CarDTO updateCar(String id, CarDTO dto){
        try {
            if (repo.existsById((id))){
                CarEntity entity = repo.getById(id);
                entity.setIdUser(dto.getIdUser());
                entity.setIdCarSize(dto.getIdCarSize());

                CarEntity savedCar = repo.save(entity);
                return CarsToDTO(savedCar);
            }
            throw new IllegalArgumentException("The car could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The car could not be updated" + e.getMessage());
        }
    }

    public boolean deleteCar(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The Car with the ID : " + id + " doesn't exists", 1);
        }
    }

    private CarDTO CarsToDTO(CarEntity entity){
        CarDTO dto = new CarDTO();
        dto.setIdCar(entity.getIdCar());
        dto.setIdUser(entity.getIdUser());
        dto.setIdCarSize(entity.getIdCarSize());
        return dto;
    }
    private CarEntity DT0ToCars(CarDTO dto){
        CarEntity entity = new CarEntity();
        entity.setIdCar(dto.getIdCar());
        entity.setIdUser(dto.getIdUser());
        entity.setIdCarSize(dto.getIdCarSize());
        return entity;
    }
}
