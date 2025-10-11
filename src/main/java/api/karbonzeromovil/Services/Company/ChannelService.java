package api.karbonzeromovil.Services.Company;

import api.karbonzeromovil.Entities.Company.ChannelEntity;
import api.karbonzeromovil.Entities.Company.CompanyEntity;
import api.karbonzeromovil.Models.DTO.Company.ChannelDTO;
import api.karbonzeromovil.Models.DTO.Company.ChannelPostDTO;
import api.karbonzeromovil.Repositories.Company.ChannelRepository;
import api.karbonzeromovil.Repositories.Company.CompanyRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service

public class ChannelService {
    @Autowired
    private ChannelRepository repo;

    @Autowired
    private CompanyRepository CompanyRepo;


    public List<ChannelDTO> getAllChannel(){
        List<ChannelEntity> channel = repo.findAll();
        return channel.stream()
                .map(this::ChannelToDTO)
                .collect(Collectors.toList());
    }

    public ChannelDTO getChannelById(String id){
        if(repo.existsById(id)){
            ChannelEntity entity = repo.getReferenceById(id);
            return ChannelToDTO(entity);
        }
        return null;
    }

    public ChannelDTO getChannelByCompany(String id) {
        CompanyEntity company = CompanyRepo.getReferenceById(id);
        return ChannelToDTO(company.getIdCompanyChannel());
    }

    public ChannelDTO getChannelByApiKey (String apikey) {
        CompanyEntity company = CompanyRepo.findByApiKey(apikey);
        return ChannelToDTO(company.getIdCompanyChannel());
    }

    public List<ChannelPostDTO> getAllPostByChannel(String apiKey){
        CompanyEntity company = CompanyRepo.findByApiKey(apiKey);
        ChannelEntity post = company.getIdCompanyChannel();
        return post.getAllChannelChannelPost().stream()
                .map(new ChannelPostService()::ChannelPostToDTO).collect(Collectors.toList());
    }

    public List<ChannelPostDTO> getNoApprovedPosts(String apiKey){
        CompanyEntity company = CompanyRepo.findByApiKey(apiKey);
        ChannelEntity post = company.getIdCompanyChannel();
        return post.getNoApprovedPosts().stream()
                .map(new ChannelPostService()::ChannelPostToDTO).collect(Collectors.toList());
    }
    public List<ChannelPostDTO> getApprovedPosts(String apiKey){
        CompanyEntity company = CompanyRepo.findByApiKey(apiKey);
        ChannelEntity post = company.getIdCompanyChannel();
        return post.getApprovedPosts().stream()
                .map(new ChannelPostService()::ChannelPostToDTO).collect(Collectors.toList());
    }
    public ChannelDTO insertChannel(ChannelDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Channel cannot be null or empty");
        }
        try {
            ChannelEntity entity = DT0ToChannel(dto);
            ChannelEntity savedChannel = repo.save(entity);
            return ChannelToDTO(savedChannel);
        } catch (Exception e) {
            throw new RuntimeException("The channel could not be created"+ e.getMessage());
        }
    }


    public ChannelDTO updateChannel(String id, ChannelDTO dto){
        try {
            if (repo.existsById((id))){
                ChannelEntity entity = repo.getReferenceById(id);
                entity.setName(dto.getName());
                entity.setDescript(dto.getDescript());
                entity.setImagePath(dto.getImagePath());
                ChannelEntity savedChannel = repo.save(entity);
                return ChannelToDTO(savedChannel);
            }
            throw new IllegalArgumentException("The channel could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The channel could not be updated" + e.getMessage());
        }
    }

    public ChannelDTO updateChannelByApiKey(String apiKey, @Valid ChannelDTO dto) {
        try {
            CompanyEntity company = CompanyRepo.findByApiKey(apiKey);
            ChannelEntity entity = company.getIdCompanyChannel();
            entity.setName(dto.getName());
            entity.setDescript(dto.getDescript());
            entity.setImagePath(dto.getImagePath());
            ChannelEntity savedChannel = repo.save(entity);
            return ChannelToDTO(savedChannel);
        }catch (Exception e){
            throw new RuntimeException("The channel could not be updated" + e.getMessage());
        }
    }

    public boolean deleteChannel(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The Channel with the ID : " + id + " doesn't exists", 1);
        }
    }


    private ChannelDTO ChannelToDTO(ChannelEntity entity){
        ChannelDTO dto = new ChannelDTO();
        dto.setIdChannel(entity.getIdChannel());
        if(entity.getIdCompany() != null){
            dto.setIdCompany(entity.getIdCompany().getIdCompany());
            dto.setCompanyName(entity.getIdCompany().getName());
        }
        dto.setName(entity.getName());
        dto.setDescript(entity.getDescript());
        dto.setImagePath(entity.getImagePath());
        return dto;
    }
    private ChannelEntity DT0ToChannel(ChannelDTO dto){
        ChannelEntity entity = new ChannelEntity();
        entity.setIdChannel(dto.getIdChannel());
        entity.setIdCompany(CompanyRepo.getReferenceById(dto.getIdCompany()));
        entity.setName(dto.getName());
        entity.setDescript(dto.getDescript());
        entity.setImagePath(dto.getImagePath());
        return entity;
    }



}
