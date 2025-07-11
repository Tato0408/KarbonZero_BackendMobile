package api.karbonzeromovil.Services.Transport;

import api.karbonzeromovil.Entities.Transport.TransportEntity;
import api.karbonzeromovil.Models.DTO.Transport.TransportDTO;
import api.karbonzeromovil.Repositories.Transport.TransportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransportService {
    @Autowired
    private TransportRepository repo;

    public List<TransportDTO> getAllTransport(){
        List<TransportEntity> countries = repo.findAll();
        return countries.stream()
                .map(this::TransportToDTO)
                .collect(Collectors.toList());
    }
    public TransportDTO insertTransport(TransportDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Transport cannot be null or empty");
        }
        try {
            TransportEntity entity = DTOToTransport(dto);
            TransportEntity savedTransport = repo.save(entity);
            return TransportToDTO(savedTransport);
        } catch (Exception e) {
            throw new RuntimeException("The Transport could not be created"+ e.getMessage());
        }
    }
    public TransportDTO updateTransport(String id, TransportDTO dto){
        try {
            if (repo.existsById((id))){
                TransportEntity entity = repo.getById(id);
                entity.setName(dto.getName());
                entity.setCarbonFootprint(dto.getCarbonFootprint());

                TransportEntity savedTransport = repo.save(entity);
                return TransportToDTO(savedTransport);
            }
            throw new IllegalArgumentException("The Transport could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The Transport could not be updated" + e.getMessage());
        }
    }
    public boolean deleteTransport(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The Transport with the ID : " + id + " doesn't exists", 1);
        }
    }
    private TransportDTO TransportToDTO(TransportEntity entity){
        TransportDTO dto = new TransportDTO();
        dto.setIdTransport(entity.getIdTransport());
        dto.setName(entity.getName());
        dto.setCarbonFootprint(entity.getCarbonFootprint());
        return dto;
    }
    private TransportEntity DTOToTransport(TransportDTO dto){
        TransportEntity entity = new TransportEntity();
        entity.setIdTransport(dto.getIdTransport());
        entity.setName(dto.getName());
        entity.setCarbonFootprint(dto.getCarbonFootprint());
        return entity;
    }
}
