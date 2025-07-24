package api.karbonzeromovil.Services.User;

import api.karbonzeromovil.Entities.User.UserAchievementEntity;
import api.karbonzeromovil.Models.DTO.User.UserAchievementDTO;
import api.karbonzeromovil.Repositories.User.UserAchievementRepository;
import api.karbonzeromovil.Repositories.User.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserAchievementService {
    @Autowired
    private UserAchievementRepository repo;
    @Autowired
    private UserRepository userRepo;

    public List<UserAchievementDTO> getAllAchievements(){
        List<UserAchievementEntity> achievements = repo.findAll();
        return achievements.stream()
                .map(this::AchievementToDTO)
                .collect(Collectors.toList());
    }
    public UserAchievementDTO insertAchievement(UserAchievementDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Achievement cannot be null or empty");
        }
        try {
            UserAchievementEntity entity = DTOToAchievement(dto);
            UserAchievementEntity savedAchievement = repo.save(entity);
            return AchievementToDTO(savedAchievement);
        } catch (Exception e) {
            throw new RuntimeException("The achievement could not be created"+ e.getMessage());
        }
    }
    public UserAchievementDTO updateAchievement(String id, UserAchievementDTO dto){
        try {
            if (repo.existsById((id))){
                UserAchievementEntity entity = repo.getReferenceById(id);
                entity.setIdUser(dto.getIdUser());
                entity.setAchievementName(dto.getAchievementName());

                UserAchievementEntity savedAchievement = repo.save(entity);
                return AchievementToDTO(savedAchievement);
            }
            throw new IllegalArgumentException("The achievement could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The achievement could not be updated" + e.getMessage());
        }
    }
    public boolean deleteAchievement(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The achievement with the ID : " + id + " doesn't exists", 1);
        }
    }
    private UserAchievementDTO AchievementToDTO(UserAchievementEntity entity){
        UserAchievementDTO dto = new UserAchievementDTO();
        dto.setIdAchievement(entity.getIdAchievement());
        dto.setIdUser(entity.getIdUser());
        dto.setAchievementName(entity.getAchievementName());
        return dto;
    }
    private UserAchievementEntity DTOToAchievement(UserAchievementDTO dto){
        UserAchievementEntity entity = new UserAchievementEntity();
        entity.setIdAchievement(dto.getIdAchievement());
        entity.setIdUser(dto.getIdUser());
        entity.setAchievementName(dto.getAchievementName());
        return entity;
    }
}
