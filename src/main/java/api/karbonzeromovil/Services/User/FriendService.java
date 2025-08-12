package api.karbonzeromovil.Services.User;

import api.karbonzeromovil.Entities.User.FriendEntity;
import api.karbonzeromovil.Entities.User.UserEntity;
import api.karbonzeromovil.Models.DTO.User.FriendDTO;
import api.karbonzeromovil.Repositories.User.FriendRepository;
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
public class FriendService {
    @Autowired
    private FriendRepository repo;
    @Autowired
    private UserRepository userRepo;

    public Page<FriendDTO> getAllFriends(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<FriendEntity> friends = repo.findAll(pageable);
        return friends.map(this::FriendToDTO);
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
                entity.setUser1(userRepo.getReferenceById(dto.getUser1()));
                entity.setUser2(userRepo.getReferenceById(dto.getUser2()));

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
        if(entity.getUser1() != null && entity.getUser2() != null){
            dto.setUser1(entity.getUser1().getIdUser());
            dto.setUser2(entity.getUser2().getIdUser());
            dto.setFriendName1(entity.getUser1().getUserName());
            dto.setFriendName2(entity.getUser2().getUserName());
        }else{
            dto.setUser1(null);
            dto.setUser2(null);
            dto.setFriendName1("No hay un usuario asociado");
            dto.setFriendName2("No hay un usuario asociado");
        }
        return dto;
    }
    private FriendEntity DTOToFriend(FriendDTO dto){
        FriendEntity entity = new FriendEntity();
        entity.setIdFriend(dto.getIdFriend());
        if(dto.getUser1() != null && dto.getUser2() != null){
            UserEntity user1 = userRepo.findById(dto.getUser1()).orElseThrow(()->new IllegalArgumentException("No hay un usuario con ese id "+dto.getUser1()));
            UserEntity user2 = userRepo.findById(dto.getUser1()).orElseThrow(()->new IllegalArgumentException("No hay un usuario con ese id "+dto.getUser2()));
            entity.setUser1(user1);
            entity.setUser2(user2);
        }
        return entity;
    }
}
