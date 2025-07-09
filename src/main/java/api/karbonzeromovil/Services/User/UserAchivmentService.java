package api.karbonzeromovil.Services.User;

import api.karbonzeromovil.Entities.User.CountryEntity;
import api.karbonzeromovil.Entities.User.UserAchivmentEntity;
import api.karbonzeromovil.Models.DTO.User.CountryDTO;
import api.karbonzeromovil.Models.DTO.User.UserAchivmentDTO;
import api.karbonzeromovil.Repositories.User.UserAchivmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserAchivmentService {
    @Autowired
    private UserAchivmentRepository repo;

    public List<UserAchivmentDTO> getAllAchivments(){
        List<UserAchivmentEntity> achivments = repo.findAll();
        return achivments.stream()
                .map(this::AchivmentToDTO)
                .collect(Collectors.toList());
    }
    public UserAchivmentDTO insertAchivment(UserAchivmentDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Country cannot be null or empty");
        }
        try {
            UserAchivmentEntity entity = DTOToAchivment(dto);
            UserAchivmentEntity savedAchivment = repo.save(entity);
            return AchivmentToDTO(savedAchivment);
        } catch (Exception e) {
            throw new RuntimeException("The achivment could not be created"+ e.getMessage());
        }
    }
    public UserAchivmentDTO updateAchivment(String id, UserAchivmentDTO dto){
        try {
            if (repo.existsById((id))){
                UserAchivmentEntity entity = repo.getById(id);
                entity.setIdUser(dto.getIdUser());
                entity.setAchivmentName(dto.getAchivmentName());

                UserAchivmentEntity savedAchivment = repo.save(entity);
                return AchivmentToDTO(savedAchivment);
            }
            throw new IllegalArgumentException("The achivment could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The achivment could not be updated" + e.getMessage());
        }
    }
    public boolean deleteAchivment(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The achivment with the ID : " + id + " doesn't exists", 1);
        }
    }
    private UserAchivmentDTO AchivmentToDTO(UserAchivmentEntity entity){
        UserAchivmentDTO dto = new UserAchivmentDTO();
        dto.setIdAchivment(entity.getIdAchivment());
        dto.setIdUser(entity.getIdUser());
        dto.setAchivmentName(entity.getAchivmentName());
        return dto;
    }
    private UserAchivmentEntity DTOToAchivment(UserAchivmentDTO dto){
        UserAchivmentEntity entity = new UserAchivmentEntity();
        entity.setIdAchivment(dto.getIdAchivment());
        entity.setIdUser(dto.getIdUser());
        entity.setAchivmentName(dto.getAchivmentName());
        return entity;
    }
}
