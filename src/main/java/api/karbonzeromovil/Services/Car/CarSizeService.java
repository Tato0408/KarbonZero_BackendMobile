package api.karbonzeromovil.Services.Car;

import api.karbonzeromovil.Entities.Car.CarSizeEntity;
import api.karbonzeromovil.Models.DTO.Car.CarSizeDTO;
import api.karbonzeromovil.Repositories.Car.CarSizeRepository;
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

public class CarSizeService {
    @Autowired
    private CarSizeRepository repo;

    public Page<CarSizeDTO> getAllCarSize(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<CarSizeEntity> entity = repo.findAll(pageable);

        return entity.map(this::CarSizeToDTO);
    }

    public CarSizeDTO getCarSizeById(String id){
        if(repo.existsById(id)){
            CarSizeEntity entity = repo.getReferenceById(id);
            return CarSizeToDTO(entity);
        }
        return null;
    }

    public CarSizeDTO insertCarSize(CarSizeDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Car size cannot be null or empty");
        }
        try {
            CarSizeEntity entity = DT0ToCarSize(dto);
            CarSizeEntity savedCarSize = repo.save(entity);
            return CarSizeToDTO(savedCarSize);
        } catch (Exception e) {
            throw new RuntimeException("The car size could not be created"+ e.getMessage());
        }
    }

    public CarSizeDTO updateSizeCar(String id, CarSizeDTO dto){
        try {
            if (repo.existsById((id))){
                CarSizeEntity entity = repo.getById(id);
                entity.setName(dto.getName());

                CarSizeEntity savedCarSize = repo.save(entity);
                return CarSizeToDTO(savedCarSize);
            }
            throw new IllegalArgumentException("The car size could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The car size could not be updated" + e.getMessage());
        }
    }

    public boolean deleteCarSize(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The Car size with the ID : " + id + " doesn't exists", 1);
        }
    }

    private CarSizeDTO CarSizeToDTO(CarSizeEntity entity){
        CarSizeDTO dto = new CarSizeDTO();
        dto.setIdCarSize(entity.getIdCarSize());
        dto.setName(entity.getName());
        return dto;
    }
    private CarSizeEntity DT0ToCarSize(CarSizeDTO dto){
        CarSizeEntity entity = new CarSizeEntity();
        entity.setIdCarSize(dto.getIdCarSize());
        entity.setName(dto.getName());
        return entity;
    }
}