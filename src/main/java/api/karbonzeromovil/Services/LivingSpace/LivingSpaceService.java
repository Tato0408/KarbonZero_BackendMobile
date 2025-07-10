package api.karbonzeromovil.Services.LivingSpace;

import api.karbonzeromovil.Entities.LivingSpace.LivingSpaceEntity;
import api.karbonzeromovil.Entities.LivingSpace.LivingSpaceTypeEntity;
import api.karbonzeromovil.Models.DTO.LivingSpace.LivingSpaceDTO;
import api.karbonzeromovil.Models.DTO.LivingSpace.LivingSpaceTypeDTO;
import api.karbonzeromovil.Repositories.LivingSpace.LivingSpaceRepository;
import api.karbonzeromovil.Repositories.LivingSpace.LivingSpaceTypeRepository;
import api.karbonzeromovil.Repositories.User.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LivingSpaceService {
    @Autowired
    private LivingSpaceRepository repo;
    @Autowired
    private LivingSpaceTypeRepository repoType;
    @Autowired
    private UserRepository repoUser;
    public List<LivingSpaceDTO> getAllLivingSpace (){
        List<LivingSpaceEntity> livingSpace = repo.findAll();
        return livingSpace.stream()
                .map(this::livingSpaceToDTO)
                .collect(Collectors.toList());
    }

    public LivingSpaceDTO insertLivingSpace(LivingSpaceDTO dto){
        if(dto == null){
            throw new IllegalArgumentException("Living space can't be null or empty");
        }
        try{
            LivingSpaceEntity entity =  DTOToLivingSpace(dto);

            LivingSpaceEntity savedEntity =  repo.save(entity);
            return livingSpaceToDTO(savedEntity);
        }
        catch(Exception e){
            throw new RuntimeException("Can't create living space " + e.getMessage());
        }
    }

    public LivingSpaceDTO updateLivingSpace(String id, LivingSpaceDTO dto){
        try{
            if(repo.existsById(id)){
                LivingSpaceEntity entity =  repo.getById(id);

                entity.setIdLivingSpaceType(repoType.getReferenceById(dto.getIdLivingSpaceType()));
                entity.setIdUser(repoUser.getReferenceById(dto.getIdUser()));

                LivingSpaceEntity savedEntity = repo.save(entity);
                return livingSpaceToDTO(savedEntity);
            }
            throw new IllegalArgumentException("The living space with the id: " + id +" doesn't exist");
        }
        catch(Exception e){
            throw new RuntimeException("Can't update living space " + e.getMessage());
        }
    }

    public boolean deleteLivingSpace(String id){
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
            throw new EmptyResultDataAccessException("The living space with the id: " + id +" doesn't exist",1);
        }
    }

    private LivingSpaceDTO livingSpaceToDTO(LivingSpaceEntity entity){
        LivingSpaceDTO dto = new LivingSpaceDTO();

        dto.setIdLivingSpace(entity.getIdLivingSpace());
        dto.setIdLivingSpaceType(entity.getIdLivingSpaceType().getIdLivingSpaceType());
        dto.setIdUser(entity.getIdUser().getIdUser());

        return dto;
    }

    private LivingSpaceEntity DTOToLivingSpace(LivingSpaceDTO dto){
        LivingSpaceEntity entity = new LivingSpaceEntity();
        entity.setIdLivingSpace(dto.getIdLivingSpace());
        entity.setIdLivingSpaceType(repoType.getReferenceById(dto.getIdLivingSpaceType()));
        entity.setIdUser(repoUser.getReferenceById(dto.getIdUser()));

        return entity;
    }
}
