package api.karbonzeromovil.Services.LivingSpace;

import api.karbonzeromovil.Entities.Food.FoodEntity;
import api.karbonzeromovil.Entities.LivingSpace.LivingSpaceEntity;
import api.karbonzeromovil.Entities.LivingSpace.LivingSpaceTypeEntity;
import api.karbonzeromovil.Entities.User.UserEntity;
import api.karbonzeromovil.Models.DTO.Food.FoodDTO;
import api.karbonzeromovil.Models.DTO.LivingSpace.LivingSpaceDTO;
import api.karbonzeromovil.Models.DTO.User.UserDTO;
import api.karbonzeromovil.Repositories.LivingSpace.LivingSpaceRepository;
import api.karbonzeromovil.Repositories.LivingSpace.LivingSpaceTypeRepository;
import api.karbonzeromovil.Repositories.User.UserRepository;
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
public class LivingSpaceService {
    @Autowired
    private LivingSpaceRepository repo;
    @Autowired
    private LivingSpaceTypeRepository repoType;
    @Autowired
    private UserRepository repoUser;
    public Page<LivingSpaceDTO> getAllLivingSpace (int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<LivingSpaceEntity> entity = repo.findAll(pageable);
        return entity.map(this::livingSpaceToDTO);
    }

    public LivingSpaceDTO getLivingSpaceById(String id){
        if(repo.existsById(id)){
            LivingSpaceEntity entity = repo.getReferenceById(id);
            return livingSpaceToDTO(entity);
        }
        return null;
    }

    public LivingSpaceDTO insertLivingSpace(LivingSpaceDTO dto){
        if(dto == null){
            throw new IllegalArgumentException("Living space can't be null or empty");
        }
        try{
            System.out.println(dto);
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
                LivingSpaceEntity entity =  repo.getReferenceById(id);
                if(dto.getIdLivingSpaceType()!= null && dto.getIdUser()!= null){
                    LivingSpaceTypeEntity type =  repoType.findById(dto.getIdLivingSpace())
                            .orElseThrow(()-> new IllegalArgumentException("Can't find the living space id"));
                    UserEntity user = repoUser.findById(dto.getIdUser())
                            .orElseThrow(()-> new IllegalArgumentException("Can't find the user id"));
                    entity.setLivingSpaceType(type);
                    entity.setIdUser(user);
                }

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

    public LivingSpaceDTO livingSpaceToDTO(LivingSpaceEntity entity) {
        LivingSpaceDTO dto = new LivingSpaceDTO();

        dto.setIdLivingSpace(entity.getIdLivingSpace());
        if (entity.getLivingSpaceType() != null && entity.getIdUser() != null) {
            dto.setIdLivingSpaceType(entity.getLivingSpaceType().getIdLivingSpaceType());
            dto.setIdUser(entity.getIdUser().getIdUser());
            dto.setLivingSpaceCo2(entity.getLivingSpaceType().getCarbonFootPrint());
            dto.setLivingSpaceTypeName(entity.getLivingSpaceType().getNameLivingSpaceType());
            dto.setUserName(entity.getIdUser().getUserName());

        } else {
            dto.setIdLivingSpaceType(null);
            dto.setIdUser(null);
            dto.setLivingSpaceTypeName("No hay un tipo de espacio habitale asignado");
            dto.setUserName("No hay un usuario asignado");
        }
        return dto;
    }
    public LivingSpaceEntity DTOToLivingSpace(LivingSpaceDTO dto){
        LivingSpaceEntity entity = new LivingSpaceEntity();
        entity.setIdLivingSpace(dto.getIdLivingSpace());
        if(dto.getIdLivingSpaceType()!= null && dto.getIdUser()!= null){
            LivingSpaceTypeEntity type =  repoType.findById(dto.getIdLivingSpaceType())
                    .orElseThrow(()-> new IllegalArgumentException("Can't find the living space id"));
            UserEntity user = repoUser.findById(dto.getIdUser())
                    .orElseThrow(()-> new IllegalArgumentException("Can't find the user id"));

            entity.setLivingSpaceType(type);
            entity.setIdUser(user);
        }

        return entity;
    }

}