package api.karbonzeromovil.Services.Company;

import api.karbonzeromovil.Entities.Company.ChannelPostEntity;
import api.karbonzeromovil.Models.DTO.Company.ChannelPostDTO;
import api.karbonzeromovil.Repositories.Company.ChannelPostRepository;
import api.karbonzeromovil.Repositories.Company.ChannelRepository;
import api.karbonzeromovil.Repositories.User.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChannelPostService {
    @Autowired
    private ChannelPostRepository repo;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private UserRepository userRepository;

    public ChannelPostDTO insertChannelPost(ChannelPostDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Post cannot be null or empty");
        }
        try {
            ChannelPostEntity entity = DTOToChannelPost(dto);
            ChannelPostEntity savedAchievement = repo.save(entity);
            return ChannelPostToDTO(savedAchievement);
        } catch (Exception e) {
            throw new RuntimeException("The Post could not be created"+ e.getMessage());
        }
    }
    public ChannelPostDTO updateChannelPost(String id, ChannelPostDTO dto){
        try {
            if (repo.existsById((id))){
                ChannelPostEntity entity = repo.getReferenceById(id);
                entity.setTitle(dto.getTitle());
                entity.setDescript(dto.getDescript());
                entity.setImagePath(dto.getImagePath());
                entity.setIsApproved(dto.getIsApproved());
                entity.setIdChannel(channelRepository.getReferenceById(dto.getIdChannel()));
                entity.setIdUser(userRepository.getReferenceById(dto.getIdUser()));

                ChannelPostEntity savedPost = repo.save(entity);
                return ChannelPostToDTO(savedPost);
            }
            throw new IllegalArgumentException("The Post could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The Post could not be updated" + e.getMessage());
        }
    }
    public boolean deleteChannelPost(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The Post with the ID : " + id + " doesn't exists", 1);
        }
    }
    public ChannelPostDTO ChannelPostToDTO(ChannelPostEntity entity){
        ChannelPostDTO dto = new ChannelPostDTO();
        dto.setIdChannelPost(entity.getIdChannelPost());
        dto.setTitle(entity.getTitle());
        dto.setDescript(entity.getDescript());
        dto.setImagePath(entity.getImagePath());
        dto.setIsApproved(entity.getIsApproved());
        dto.setIdChannel(entity.getIdChannel().getIdChannel());
        dto.setIdUser(entity.getIdUser().getIdUser());
        return dto;
    }
    public ChannelPostEntity DTOToChannelPost(ChannelPostDTO dto){
        ChannelPostEntity entity = new ChannelPostEntity();
        entity.setIdChannelPost(dto.getIdChannelPost());
        entity.setTitle(dto.getTitle());
        entity.setDescript(dto.getDescript());
        entity.setImagePath(dto.getImagePath());
        entity.setIsApproved(dto.getIsApproved());
        entity.setIdChannel(channelRepository.getReferenceById(dto.getIdChannel()));
        entity.setIdUser(userRepository.getReferenceById(dto.getIdUser()));
        return entity;
    }
}
