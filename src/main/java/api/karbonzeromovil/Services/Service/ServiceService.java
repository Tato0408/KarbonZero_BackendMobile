package api.karbonzeromovil.Services.Service;

import api.karbonzeromovil.Entities.Service.ServiceEntity;
import api.karbonzeromovil.Models.DTO.Service.ServiceDTO;
import api.karbonzeromovil.Repositories.Service.ServiceRepository;
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
public class ServiceService {
    @Autowired
    private ServiceRepository repo;

    public Page<ServiceDTO> getAllServices(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<ServiceEntity> users = repo.findAll(pageable);
        return users.map(this::ServiceToDTO);
    }
    public List<ServiceDTO> getAllServiceNP(){
        List<ServiceEntity> entity = repo.findAll();
        return entity.stream()
                .map(this::ServiceToDTO)
                .collect(Collectors.toList());
    }
    public ServiceDTO getServiceById(String id){
        try {
            if(repo.existsById(id)){
                ServiceEntity entity = repo.getReferenceById(id);
                return ServiceToDTO(entity);
            }
            throw new IllegalArgumentException("The Service with the id: "+id+" does not exist, check if it exist");
        }catch (Exception err) {
            throw new RuntimeException("The Service could not be found" + err.getMessage());

        }
    }

    public Page<ServiceDTO> getServiceByName(String serviceName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ServiceEntity> services = repo.findByNameContains(serviceName, pageable);
        return services.map(this::ServiceToDTO);
    }
    public ServiceDTO insertService(ServiceDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Service cannot be null or empty");
        }
        try {
            ServiceEntity entity = DTOToService(dto);
            ServiceEntity savedService = repo.save(entity);
            return ServiceToDTO(savedService);
        } catch (Exception e) {
            throw new RuntimeException("The Service could not be created"+ e.getMessage());
        }
    }
    public ServiceDTO updateService(String id, ServiceDTO dto){
        try {
            if (repo.existsById((id))){
                ServiceEntity entity = repo.getReferenceById(id);
                entity.setName(dto.getName());
                ServiceEntity savedService = repo.save(entity);
                return ServiceToDTO(savedService);
            }
            throw new IllegalArgumentException("The Service could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The Service could not be updated" + e.getMessage());
        }
    }
    public boolean deleteService(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The Service with the ID : " + id + " doesn't exists", 1);
        }
    }
    private ServiceDTO ServiceToDTO(ServiceEntity entity){
        ServiceDTO dto = new ServiceDTO();
        dto.setIdService(entity.getIdService());
        dto.setName(entity.getName());
        return dto;
    }
    private ServiceEntity DTOToService(ServiceDTO dto){
        ServiceEntity entity = new ServiceEntity();
        entity.setIdService(dto.getIdService());
        entity.setName(dto.getName());
        return entity;
    }
}
