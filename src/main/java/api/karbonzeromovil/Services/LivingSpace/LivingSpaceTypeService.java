package api.karbonzeromovil.Services.LivingSpace;

import api.karbonzeromovil.Entities.Food.FoodEntity;
import api.karbonzeromovil.Entities.LivingSpace.LivingSpaceEntity;
import api.karbonzeromovil.Entities.LivingSpace.LivingSpaceTypeEntity;
import api.karbonzeromovil.Models.DTO.Food.FoodDTO;
import api.karbonzeromovil.Models.DTO.LivingSpace.LivingSpaceDTO;
import api.karbonzeromovil.Models.DTO.LivingSpace.LivingSpaceTypeDTO;
import api.karbonzeromovil.Repositories.LivingSpace.LivingSpaceTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.MutuallyExclusiveConfigurationPropertiesException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LivingSpaceTypeService {
    @Autowired
    private LivingSpaceTypeRepository repo;

    public Page<LivingSpaceTypeDTO> getAllLivingSpaceType (int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<LivingSpaceTypeEntity> entity = repo.findAll(pageable);
        return entity.map(this::livingSpaceTypeToDTO);
    }

    public LivingSpaceTypeDTO getLivingSpaceTypeById(String id){
        if(repo.existsById(id)){
            LivingSpaceTypeEntity entity = repo.getReferenceById(id);
            return livingSpaceTypeToDTO(entity);
        }
        return null;
    }

    public LivingSpaceTypeDTO insertLivingSpaceType(LivingSpaceTypeDTO dto){
        if(dto == null){
            throw new IllegalArgumentException("Living space type can't be null or empty");
        }
        try{
            LivingSpaceTypeEntity entity =  DTOToLivingSpaceType(dto);

            LivingSpaceTypeEntity savedEntity =  repo.save(entity);
            return livingSpaceTypeToDTO(savedEntity);
        }
        catch(Exception e){
            throw new RuntimeException("Can't create living space type " + e.getMessage());
        }
    }

    public LivingSpaceTypeDTO updateLivingSpaceType(String id, LivingSpaceTypeDTO dto){
        try{
            if(repo.existsById(id)){
                LivingSpaceTypeEntity entity =  repo.getReferenceById(id);

                entity.setNameLivingSpaceType(dto.getNameLivingSpaceType());
                entity.setCarbonFootPrint(dto.getCarbonFootPrint());

                LivingSpaceTypeEntity savedEntity = repo.save(entity);
                return livingSpaceTypeToDTO(savedEntity);
            }
            throw new IllegalArgumentException("The living space type with the id: " + id +" doesn't exist");
        }
        catch(Exception e){
            throw new RuntimeException("Can't update living space type" + e.getMessage());
        }
    }

    public boolean deleteLivingSpaceType(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }
            else{
                return false;
            }
        }
        catch(EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The living space type with the id: " + id +" doesn't exist",1);
        }
    }

    private LivingSpaceTypeDTO livingSpaceTypeToDTO(LivingSpaceTypeEntity entity){
        LivingSpaceTypeDTO dto = new LivingSpaceTypeDTO();

        dto.setIdLivingSpaceType(entity.getIdLivingSpaceType());
        dto.setNameLivingSpaceType(entity.getNameLivingSpaceType());
        dto.setCarbonFootPrint(entity.getCarbonFootPrint());

        return dto;
    }

    private LivingSpaceTypeEntity DTOToLivingSpaceType(LivingSpaceTypeDTO dto){
        LivingSpaceTypeEntity entity = new LivingSpaceTypeEntity();
        entity.setIdLivingSpaceType(dto.getIdLivingSpaceType());
        entity.setNameLivingSpaceType(dto.getNameLivingSpaceType());
        entity.setCarbonFootPrint(dto.getCarbonFootPrint());

        return entity;
    }
}
