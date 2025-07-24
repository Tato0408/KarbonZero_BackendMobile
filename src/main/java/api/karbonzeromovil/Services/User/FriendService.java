package api.karbonzeromovil.Services.User;

import api.karbonzeromovil.Entities.User.FriendEntity;
import api.karbonzeromovil.Models.DTO.User.FriendDTO;
import api.karbonzeromovil.Repositories.User.FriendRepository;
import api.karbonzeromovil.Repositories.User.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FriendService {
    @Autowired
    private FriendRepository repo;
    @Autowired
    private UserRepository userRepo;

    public List<FriendDTO> getAllFriends(){
        List<FriendEntity> friends = repo.findAll();
        return friends.stream()
                .map(this::FriendToDTO)
                .collect(Collectors.toList());
    }
    public FriendDTO insertFriend(FriendDTO dto)throws IllegalAccessException{
        if (dto ==null){
            throw new IllegalAccessException("Friend cannot be null");
        }
        try{
            FriendEntity entity =DTOToFriend(dto);
            FriendEntity savedFriend = repo.save(entity);
            return FriendToDTO(savedFriend);
        }catch (Exception e){
            throw new RuntimeException("The new bound could not be created"+ e.getMessage());
        }
    }
    public FriendDTO updateFriend(String id, FriendDTO dto){
        try{
            if (repo.existsById(id)){
                FriendEntity entity = repo.getReferenceById(id);
                entity.setUser1(dto.getUser1());
                entity.setUser2(dto.getUser2());

                FriendEntity savedFriend = repo.save(entity);
                return FriendToDTO(savedFriend);
            }
            throw new IllegalArgumentException("The bound could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The friend could not be updated" + e.getMessage());
        }
    }
    public boolean deleteFriend(String id){
        try {
            if (repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The bound with the ID : " + id + " doesn't exists", 1);
        }
    }
    private FriendDTO FriendToDTO(FriendEntity entity){
        FriendDTO dto = new FriendDTO();
        dto.setIdFriend(entity.getIdFriend());
        dto.setUser1(entity.getUser1());
        dto.setUser2(entity.getUser2());
        return dto;
    }
    private FriendEntity DTOToFriend(FriendDTO dto){
        FriendEntity entity = new FriendEntity();
        entity.setIdFriend(dto.getIdFriend());
        entity.setUser1(dto.getUser1());
        entity.setUser2(dto.getUser2());
        return entity;
    }
}
