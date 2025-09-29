package api.karbonzeromovil.Services.Service;

import api.karbonzeromovil.Entities.Service.ServiceConsumptionEntity;
import api.karbonzeromovil.Models.DTO.Service.ServiceConsumptionDTO;
import api.karbonzeromovil.Repositories.Service.MeasureUnitServiceRepository;
import api.karbonzeromovil.Repositories.Service.ServiceConsumptionRepository;
import api.karbonzeromovil.Repositories.User.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class ServiceConsumptionService {
    @Autowired
    private ServiceConsumptionRepository repo;
    @Autowired
    private MeasureUnitServiceRepository repoMeasureUnit;
    @Autowired
    private UserRepository repoUser;

    public Page<ServiceConsumptionDTO> getAllServiceConsumption(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<ServiceConsumptionEntity> service = repo.findAll(pageable);
        return service.map(this::ServiceConsumptionToDTO);
    }
    public ServiceConsumptionDTO getServiceConsumptionById(String id){
        try {
            if(repo.existsById(id)){
                ServiceConsumptionEntity entity = repo.getReferenceById(id);
                return ServiceConsumptionToDTO(entity);
            }
            throw new IllegalArgumentException("The Consumption with the id: "+id+" does not exist, check if it exist");
        }catch (Exception err) {
            throw new RuntimeException("The Consumption could not be found" + err.getMessage());

        }
    }

    public List<HashMap<?,?>> getServiceConsumptionsCO2WithLocation(Double minX, Double maxX, Double minY, Double maxY) {
        List<HashMap<?, ?>> consumptionsWithLocation = new ArrayList<>();

        // Filtramos los consumos de servicio para mejor rendimiento
        repo.findByLongitudeBetweenAndLatitudeBetween(minX, maxX, minY, maxY)
                .forEach(consumption -> {
                    // Devolvemos la respuesta en un formato más limpio
                    HashMap<String, Double> answer = new HashMap<>();
                    answer.put("longitude", consumption.getLongitude());
                    answer.put("latitude", consumption.getLatitude());
                    answer.put("total", consumption.getQuantity() * consumption.getIdMeasureUnitService().getCarbonFootprint());
                    consumptionsWithLocation.add(answer);
                });
        return consumptionsWithLocation;
    }

    public ServiceConsumptionDTO insertServiceConsumption(ServiceConsumptionDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Service Consumption cannot be null or empty");
        }
        try {
            ServiceConsumptionEntity entity = DTOToServiceConsumption(dto);
            ServiceConsumptionEntity consumption = repo.save(entity);
            return ServiceConsumptionToDTO(consumption);
        } catch (Exception e) {
            throw new RuntimeException("The Service Consumption could not be created"+ e.getMessage());
        }
    }
    public ServiceConsumptionDTO updateServiceConsumption(String id, ServiceConsumptionDTO dto){
        try {
            if (repo.existsById((id))){
                ServiceConsumptionEntity entity = repo.getReferenceById(id);
                entity.setIdMeasureUnitService(repoMeasureUnit.getReferenceById(dto.getIdMeasureUnitService()));
                entity.setIdUser(repoUser.getReferenceById(dto.getIdUser()));
                entity.setQuantity(dto.getQuantity());
                entity.setConsumptionDate(dto.getConsumptionDate());
                entity.setLatitude(dto.getLatitude());
                entity.setLongitude(dto.getLongitude());

                ServiceConsumptionEntity savedConsumption = repo.save(entity);
                return ServiceConsumptionToDTO(savedConsumption);
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
    public ServiceConsumptionDTO ServiceConsumptionToDTO(ServiceConsumptionEntity entity){
        ServiceConsumptionDTO dto = new ServiceConsumptionDTO();
        dto.setIdServiceConsumption(entity.getIdServiceConsumption());
        if(entity.getIdMeasureUnitService() != null){
            dto.setIdMeasureUnitService(entity.getIdMeasureUnitService().getIdMeasureUnitService());
            dto.setServiceName(entity.getIdMeasureUnitService().getIdService().getName());
            dto.setMeasureUnitName(entity.getIdMeasureUnitService().getIdMeasureUnit().getNameMeasuresUnit());
        }
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
        entity.setIdMeasureUnitService(repoMeasureUnit.getReferenceById(dto.getIdMeasureUnitService()));
        entity.setIdUser(repoUser.getReferenceById(dto.getIdUser()));
        entity.setQuantity(dto.getQuantity());
        entity.setConsumptionDate(dto.getConsumptionDate());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        return entity;
    }


}
