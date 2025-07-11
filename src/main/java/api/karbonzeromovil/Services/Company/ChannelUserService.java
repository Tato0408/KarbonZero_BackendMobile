package api.karbonzeromovil.Services.Company;

import api.karbonzeromovil.Entities.Company.ChannelUserEntity;
import api.karbonzeromovil.Models.DTO.Company.ChannelUserDTO;
import api.karbonzeromovil.Repositories.Company.ChannelUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChannelUserService {

    private ChannelUserRepository repo;


    public List<ChannelUserDTO> getAllChannelUser(){
        List<ChannelUserEntity> company = repo.findAll();
        return company.stream()
                .map(this::ChannelUserToDTO)
                .collect(Collectors.toList());
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
                entity.setIdChannel(dto.getIdChannel());
                entity.setIdUser(dto.getIdUser());

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


    private ChannelUserDTO ChannelUserToDTO(ChannelUserEntity entity){
        ChannelUserDTO dto = new ChannelUserDTO();
        dto.setIdChannelUser(entity.getIdChannelUser());
        dto.setIdChannel(entity.getIdChannel());
        dto.setIdUser(entity.getIdUser());
        return dto;
    }
    private ChannelUserEntity DT0ToChannelUser(ChannelUserDTO dto){
        ChannelUserEntity entity = new ChannelUserEntity();
        entity.setIdChannelUser(dto.getIdChannelUser());
        entity.setIdChannel(dto.getIdChannel());
        entity.setIdUser(dto.getIdUser());
        return entity;
    }
}
