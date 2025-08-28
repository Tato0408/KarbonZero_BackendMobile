package api.karbonzeromovil.Services.Transport;

import api.karbonzeromovil.Entities.Transport.TransportConsumptionEntity;
import api.karbonzeromovil.Models.DTO.Transport.TransportConsumptionDTO;
import api.karbonzeromovil.Repositories.Transport.TransportConsumptionRepository;
import api.karbonzeromovil.Repositories.Transport.TransportRepository;
import api.karbonzeromovil.Repositories.User.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransportConsumptionService {
    @Autowired
    private TransportConsumptionRepository repo;
    @Autowired
    private UserRepository repoUser;
    @Autowired
    private TransportRepository repoTransport;

    public Page<TransportConsumptionDTO> getAllTransportConsumption(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<TransportConsumptionEntity> users = repo.findAll(pageable);
        return users.map(this::TransportConsumptionToDTO);
    }
    public TransportConsumptionDTO getTransportConsumptionById(String id){
        try {
            if(repo.existsById(id)){
                TransportConsumptionEntity entity = repo.getReferenceById(id);
                return TransportConsumptionToDTO(entity);
            }
            throw new IllegalArgumentException("The Consumption with the id: "+id+" does not exist, check if it exist");
        }catch (Exception err) {
            throw new RuntimeException("The Consumption could not be found" + err.getMessage());

        }
    }
    public TransportConsumptionDTO insertTransportConsumption(TransportConsumptionDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Transport Consumption cannot be null or empty");
        }
        try {
            TransportConsumptionEntity entity = DTOToTransportConsumption(dto);
            TransportConsumptionEntity consumption = repo.save(entity);
            return TransportConsumptionToDTO(consumption);
        } catch (Exception e) {
            throw new RuntimeException("The Transport Consumption could not be created"+ e.getMessage());
        }
    }
    public TransportConsumptionDTO updateTransportConsumption(String id, TransportConsumptionDTO dto){
        try {
            if (repo.existsById((id))){
                TransportConsumptionEntity entity = repo.getReferenceById(id);
                entity.setIdTransport(repoTransport.getReferenceById(dto.getIdTransport()));
                entity.setIdUser(repoUser.getReferenceById(dto.getIdUser()));
                entity.setDistance(dto.getDistance());
                entity.setConsumptionDate(dto.getConsumptionDate());
                entity.setLatitude(dto.getLatitude());
                entity.setLongitude(dto.getLongitude());

                TransportConsumptionEntity savedTransportConsumption = repo.save(entity);
                return TransportConsumptionToDTO(savedTransportConsumption);
            }
            throw new IllegalArgumentException("The Transport Consumption could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The Transport Consumption could not be updated" + e.getMessage());
        }
    }
    public boolean deleteTransportConsumption(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The Transport Consumption with the ID : " + id + " doesn't exists", 1);
        }
    }
    public TransportConsumptionDTO TransportConsumptionToDTO(TransportConsumptionEntity entity){
        TransportConsumptionDTO dto = new TransportConsumptionDTO();
        dto.setIdTransportConsumption(entity.getIdTransportConsumption());
        if(entity.getIdTransport() != null){
            dto.setIdTransport(entity.getIdTransport().getIdTransport());
            dto.setTransportName(entity.getIdTransport().getName());
        }
        dto.setIdUser(entity.getIdUser().getIdUser());
        dto.setDistance(entity.getDistance());
        dto.setConsumptionDate(entity.getConsumptionDate());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        return dto;
    }
    private TransportConsumptionEntity DTOToTransportConsumption(TransportConsumptionDTO dto){
        TransportConsumptionEntity entity = new TransportConsumptionEntity();
        entity.setIdTransportConsumption(dto.getIdTransportConsumption());
        entity.setIdTransport(repoTransport.getReferenceById(dto.getIdTransport()));
        entity.setIdUser(repoUser.getReferenceById(dto.getIdUser()));
        entity.setDistance(dto.getDistance());
        entity.setConsumptionDate(dto.getConsumptionDate());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        return entity;
    }
}
