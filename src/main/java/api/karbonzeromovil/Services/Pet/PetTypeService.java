package api.karbonzeromovil.Services.Pet;

import api.karbonzeromovil.Entities.Pet.PetTypeEntity;
import api.karbonzeromovil.Models.DTO.Pet.PetTypeDTO;
import api.karbonzeromovil.Repositories.Pet.PetTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PetTypeService {
    @Autowired
    private PetTypeRepository repo;

    public List<PetTypeDTO> getAllPetType (){
        List<PetTypeEntity> petType = repo.findAll();
        return petType.stream()
                .map(this::PetTypeToDTO)
                .collect(Collectors.toList());
    }

    public PetTypeDTO insertPetType(PetTypeDTO dto){
        if(dto == null){
            throw new IllegalArgumentException("Pet type can't be null or empty");
        }
        try{
            PetTypeEntity entity = DTOToPetType(dto);
            PetTypeEntity savedEntity = repo.save(entity);
            return PetTypeToDTO(savedEntity);
        }
        catch (Exception e){
            throw new RuntimeException("Can't create Pet Type " + e.getMessage());
        }
    }

    public PetTypeDTO updatePetType(String id, PetTypeDTO dto){
        try{
            if(repo.existsById(id)){
                PetTypeEntity entity =  repo.getById(id);
                entity.setNamePetType(dto.getNamePetType());
                entity.setCarbonFootPrint(dto.getCarbonFootPrint());
                PetTypeEntity savedEntity = repo.save(entity);
                return PetTypeToDTO(savedEntity);
            }
            throw new IllegalArgumentException("The Pet Type with the id: " + id +" doesn't exist");
        }
        catch(Exception e){
            throw new RuntimeException("Can't update pet type" + e.getMessage());
        }
    }
    public boolean deletePetType(String id){
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
            throw new EmptyResultDataAccessException("The Pet Type with the id: " + id +" doesn't exist",1);
        }
    }
    private PetTypeDTO PetTypeToDTO(PetTypeEntity entity){
        PetTypeDTO dto = new PetTypeDTO();
        dto.setIdPetType(entity.getIdPetType());
        dto.setCarbonFootPrint(entity.getCarbonFootPrint());
        return dto;
    }
    private PetTypeEntity DTOToPetType(PetTypeDTO dto){
        PetTypeEntity entity = new PetTypeEntity();
        entity.setIdPetType(dto.getIdPetType());
        dto.setCarbonFootPrint(entity.getCarbonFootPrint());
        return entity;
    }
}
