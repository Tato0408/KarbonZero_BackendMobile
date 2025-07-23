package api.karbonzeromovil.Services.Service;


import api.karbonzeromovil.Entities.Service.ServiceConsumptionEntity;
import api.karbonzeromovil.Models.DTO.Service.ServiceConsumptionDTO;
import api.karbonzeromovil.Repositories.Service.ServiceConsumptionRepository;
import api.karbonzeromovil.Repositories.User.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServiceConsumptionService {
    @Autowired
    private ServiceConsumptionRepository repo;
    @Autowired
    private UserRepository repoUser;

    public List<ServiceConsumptionDTO> getAllServiceConsumption(){
        List<ServiceConsumptionEntity> achievements = repo.findAll();
        return achievements.stream()
                .map(this::ServiceConsumptionToDTO)
                .collect(Collectors.toList());
    }
    public ServiceConsumptionDTO insertServiceConsumption(ServiceConsumptionDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Service Consumption cannot be null or empty");
        }
        try {
            ServiceConsumptionEntity entity = DTOToServiceConsumption(dto);
            ServiceConsumptionEntity savedAchievement = repo.save(entity);
            return ServiceConsumptionToDTO(savedAchievement);
        } catch (Exception e) {
            throw new RuntimeException("The Service Consumption could not be created"+ e.getMessage());
        }
    }
    public ServiceConsumptionDTO updateServiceConsumption(String id, ServiceConsumptionDTO dto){
        try {
            if (repo.existsById((id))){
                ServiceConsumptionEntity entity = repo.getReferenceById(id);
                entity.setIdMeasureUnitService(dto.getIdMeasureUnitService());
                entity.setIdUser(repoUser.getReferenceById(dto.getIdUser()));
                entity.setQuantity(dto.getQuantity());
                entity.setConsumptionDate(dto.getConsumptionDate());
                entity.setLatitude(dto.getLatitude());
                entity.setLongitude(dto.getLongitude());

                ServiceConsumptionEntity savedServiceConsumption = repo.save(entity);
                return ServiceConsumptionToDTO(savedServiceConsumption);
            }
            throw new IllegalArgumentException("The Service Consumption could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The Service Consumption could not be updated" + e.getMessage());
        }
    }
    public boolean deleteServiceConsumption(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The Service Consumption with the ID : " + id + " doesn't exists", 1);
        }
    }
    private ServiceConsumptionDTO ServiceConsumptionToDTO(ServiceConsumptionEntity entity){
        ServiceConsumptionDTO dto = new ServiceConsumptionDTO();
        dto.setIdServiceConsumption(entity.getIdServiceConsumption());
        dto.setIdMeasureUnitService(entity.getIdMeasureUnitService());
        dto.setIdUser(entity.getIdUser().getIdUser());
        dto.setQuantity(entity.getQuantity());
        dto.setConsumptionDate(entity.getConsumptionDate());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        return dto;
    }
    private ServiceConsumptionEntity DTOToServiceConsumption(ServiceConsumptionDTO dto){
        ServiceConsumptionEntity entity = new ServiceConsumptionEntity();
        entity.setIdServiceConsumption(dto.getIdServiceConsumption());
        entity.setIdMeasureUnitService(dto.getIdMeasureUnitService());
        entity.setIdUser(repoUser.getReferenceById(dto.getIdUser()));
        entity.setQuantity(dto.getQuantity());
        entity.setConsumptionDate(dto.getConsumptionDate());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        return entity;
    }
}
