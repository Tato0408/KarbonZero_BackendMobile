package api.karbonzeromovil.Services.Pet;

import api.karbonzeromovil.Entities.Measure.MeasureEntity;
import api.karbonzeromovil.Entities.Pet.PetTypeEntity;
import api.karbonzeromovil.Entities.Pet.UserPetEntity;
import api.karbonzeromovil.Models.DTO.Measure.MeasureDTO;
import api.karbonzeromovil.Models.DTO.Pet.PetTypeDTO;
import api.karbonzeromovil.Models.DTO.Pet.UserPetDTO;
import api.karbonzeromovil.Repositories.Pet.PetTypeRepository;
import api.karbonzeromovil.Repositories.Pet.UserPetRepository;
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
public class UserPetService {
    @Autowired
    private PetTypeRepository repoType;
    @Autowired
    private UserPetRepository repo;
    @Autowired
    private UserRepository repoUser;

    public Page<UserPetDTO> getAllUserPets (int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<UserPetEntity> entity = repo.findAll(pageable);
        return entity.map(this::UserPetToDTO);
    }

    public UserPetDTO getUserPetById(String id){
        if(repo.existsById(id)){
            UserPetEntity entity = repo.getReferenceById(id);
            return UserPetToDTO(entity);
        }
        return null;
    }
    public UserPetDTO insertUserPet(UserPetDTO dto){
        if(dto == null){
            throw new IllegalArgumentException("Pet user can't be null or empty");
        }
        try{
            UserPetEntity entity = DTOToUserPet(dto);
            UserPetEntity savedEntity = repo.save(entity);
            return UserPetToDTO(savedEntity);
        }
        catch (Exception e){
            throw new RuntimeException("Can't create Pet user " + e.getMessage());
        }
    }

    public UserPetDTO updateUserPet(String id, UserPetDTO dto){
        try{
            if(repo.existsById(id)){
                UserPetEntity entity =  repo.getReferenceById(id);

                entity.setIdPetType(repoType.getReferenceById(dto.getIdPetType()));
                entity.setIdUser(repoUser.getReferenceById(dto.getIdUser()));
                UserPetEntity savedEntity = repo.save(entity);
                return UserPetToDTO(savedEntity);
            }
            throw new IllegalArgumentException("The Pet user with the id: " + id +" doesn't exist");
        }
        catch(Exception e){
            throw new RuntimeException("Can't update pet user" + e.getMessage());
        }
    }
    public boolean deleteUserPet(String id){
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
            throw new EmptyResultDataAccessException("The Pet User with the id: " + id +" doesn't exist",1);
        }
    }
    public UserPetDTO UserPetToDTO(UserPetEntity entity){
        UserPetDTO dto = new UserPetDTO();

        dto.setIdUserPet(entity.getIdUserPet());
        dto.setIdPetType(entity.getIdPetType().getIdPetType());
        dto.setIdUser(entity.getIdUser().getIdUser());

        return dto;
    }
    private UserPetEntity DTOToUserPet(UserPetDTO dto){
        UserPetEntity entity = new UserPetEntity();

        entity.setIdUserPet(dto.getIdUserPet());
        entity.setIdPetType(repoType.getReferenceById(dto.getIdPetType()));
        entity.setIdUser(repoUser.getReferenceById(dto.getIdUser()));

        return entity;
    }
}
