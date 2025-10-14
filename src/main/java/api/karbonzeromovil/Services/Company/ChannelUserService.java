package api.karbonzeromovil.Services.Company;

import api.karbonzeromovil.Entities.Company.ChannelUserEntity;
import api.karbonzeromovil.Models.DTO.Company.ChannelUserDTO;
import api.karbonzeromovil.Repositories.Company.ChannelRepository;
import api.karbonzeromovil.Repositories.Company.ChannelUserRepository;
import api.karbonzeromovil.Repositories.User.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChannelUserService {
    @Autowired
    private ChannelUserRepository repo;

    @Autowired
    private ChannelRepository ChannelRepo;

    @Autowired
    private UserRepository UserRepo;


    public List<ChannelUserDTO> getAllChannelUser(){
        List<ChannelUserEntity> company = repo.findAll();
        return company.stream()
                .map(this::ChannelUserToDTO)
                .collect(Collectors.toList());
    }

    public ChannelUserDTO getChannelUserById(String id){
        if(repo.existsById(id)){
            ChannelUserEntity entity = repo.getReferenceById(id);
            return ChannelUserToDTO(entity);
        }
        return null;
    }
    public boolean getUserChannelRelationBool(String idChannel, String idUser){
        return repo.existsByIdChannel_IdChannelAndIdUser_IdUser(idChannel, idUser);
    }

    public ChannelUserDTO getUserChannelRelationObj(String idChannel, String idUser){
        return ChannelUserToDTO(repo.findByIdChannel_IdChannelAndIdUser_IdUser(idChannel, idUser));
    }


    public ChannelUserDTO insertChannelUser(ChannelUserDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Channel user cannot be null or empty");
        }
        try {
            ChannelUserEntity entity = DT0ToChannelUser(dto);
            ChannelUserEntity savedChannelUser = repo.save(entity);
            return ChannelUserToDTO(savedChannelUser);
        } catch (Exception e) {
            throw new RuntimeException("The channel user could not be created"+ e.getMessage());
        }
    }


    public ChannelUserDTO updateChannelUser(String id, ChannelUserDTO dto){
        try {
            if (repo.existsById((id))){
                ChannelUserEntity entity = repo.getById(id);
                entity.setIdChannel(ChannelRepo.getReferenceById(dto.getIdChannel()));
                entity.setIdUser(UserRepo.getReferenceById(dto.getIdUser()));

                ChannelUserEntity savedChannelUser = repo.save(entity);
                return ChannelUserToDTO(savedChannelUser);
            }
            throw new IllegalArgumentException("The channel user could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The channel user could not be updated" + e.getMessage());
        }
    }

    public boolean deleteChannelUser(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The channel user with the ID : " + id + " doesn't exists", 1);
        }
    }


    public ChannelUserDTO ChannelUserToDTO(ChannelUserEntity entity){
        ChannelUserDTO dto = new ChannelUserDTO();
        dto.setIdChannelUser(entity.getIdChannelUser());
        if(entity.getIdChannel() != null){
            dto.setIdChannel(entity.getIdChannel().getIdChannel());
            dto.setName(entity.getIdChannel().getName());
            dto.setChannelImage(entity.getIdChannel().getImagePath());
        }
        dto.setIdUser(entity.getIdUser().getIdUser());
        return dto;
    }
    private ChannelUserEntity DT0ToChannelUser(ChannelUserDTO dto){
        ChannelUserEntity entity = new ChannelUserEntity();
        entity.setIdChannelUser(dto.getIdChannelUser());
        entity.setIdChannel(ChannelRepo.getReferenceById(dto.getIdChannel()));
        entity.setIdUser(UserRepo.getReferenceById(dto.getIdUser()));
        return entity;
    }
}
