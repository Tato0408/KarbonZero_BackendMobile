package api.karbonzeromovil.Services.Service;

import api.karbonzeromovil.Entities.Service.MeasureUnitServiceEntity;
import api.karbonzeromovil.Entities.Service.ServiceEntity;
import api.karbonzeromovil.Models.DTO.Service.MeasureUnitServiceDTO;
import api.karbonzeromovil.Repositories.Measure.MeasureUnitRepository;
import api.karbonzeromovil.Repositories.Service.MeasureUnitServiceRepository;
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
public class MeasureUnitServiceService {
    @Autowired
    private MeasureUnitServiceRepository repo;
    @Autowired
    private ServiceRepository serviceRepo;
    @Autowired
    private MeasureUnitRepository measureUnitRepo;

    public Page<MeasureUnitServiceDTO> getAllMeasureUnitService(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<MeasureUnitServiceEntity> users = repo.findAll(pageable);
        return users.map(this::MeasureUnitServiceToDTO);
    }
    public MeasureUnitServiceDTO getMeasureUnitServiceById(String id){
        try {
            if(repo.existsById(id)){
                MeasureUnitServiceEntity entity = repo.getReferenceById(id);
                return MeasureUnitServiceToDTO(entity);
            }
            throw new IllegalArgumentException("The Measure with the id: "+id+" does not exist, check if it exist");
        }catch (Exception err) {
            throw new RuntimeException("The Measure could not be found" + err.getMessage());

        }
    }
    public Page<MeasureUnitServiceDTO> getMeasureUnitServiceByName(String serviceName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ServiceEntity> services = serviceRepo.findByNameContains(serviceName);

        return repo.findByIdServiceIn(services, pageable).map(this::MeasureUnitServiceToDTO);
    }
    public MeasureUnitServiceDTO insertMeasureUnitService(MeasureUnitServiceDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Measure Unit Service cannot be null or empty");
        }
        try {
            MeasureUnitServiceEntity entity = DTOToMeasureUnitService(dto);
            MeasureUnitServiceEntity savedAchievement = repo.save(entity);
            return MeasureUnitServiceToDTO(savedAchievement);
        } catch (Exception e) {
            throw new RuntimeException("The Measure Unit Service could not be created"+ e.getMessage());
        }
    }
    public MeasureUnitServiceDTO updateMeasureUnitService(String id, MeasureUnitServiceDTO dto){
        try {
            if (repo.existsById((id))){
                MeasureUnitServiceEntity entity = repo.getReferenceById(id);
                entity.setIdService(serviceRepo.getReferenceById(dto.getIdService()));
                entity.setIdMeasureUnit(measureUnitRepo.getReferenceById(dto.getIdMeasureUnit()));
                entity.setCarbonFootprint(dto.getCarbonFootprint());

                MeasureUnitServiceEntity savedMeasureUnitService = repo.save(entity);
                return MeasureUnitServiceToDTO(savedMeasureUnitService);
            }
            throw new IllegalArgumentException("The Measure Unit Service could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The Measure Unit Service could not be updated" + e.getMessage());
        }
    }
    public boolean deleteMeasureUnitService(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The Measure Unit Service with the ID : " + id + " doesn't exists", 1);
        }
    }
    private MeasureUnitServiceDTO MeasureUnitServiceToDTO(MeasureUnitServiceEntity entity){
        MeasureUnitServiceDTO dto = new MeasureUnitServiceDTO();
        dto.setIdMeasureUnitService(entity.getIdMeasureUnitService());
        if(entity.getIdMeasureUnit() != null && entity.getIdService() != null){
            dto.setIdService(entity.getIdService().getIdService());
            dto.setIdMeasureUnit(entity.getIdMeasureUnit().getIdMeasureUnit());
            dto.setServiceName(entity.getIdService().getName());
            dto.setMeasureUnitName(entity.getIdMeasureUnit().getNameMeasuresUnit());
        }else{
            dto.setIdService(null);
            dto.setIdMeasureUnit(null);
            dto.setServiceName("No hay relacion");
            dto.setMeasureUnitName("No hay relacion");
        }
        dto.setCarbonFootprint(entity.getCarbonFootprint());
        return dto;
    }
    private MeasureUnitServiceEntity DTOToMeasureUnitService(MeasureUnitServiceDTO dto){
        MeasureUnitServiceEntity entity = new MeasureUnitServiceEntity();
        entity.setIdMeasureUnitService(dto.getIdMeasureUnitService());
        entity.setIdService(serviceRepo.getReferenceById(dto.getIdService()));
        entity.setIdMeasureUnit(measureUnitRepo.getReferenceById(dto.getIdMeasureUnit()));
        entity.setCarbonFootprint(dto.getCarbonFootprint());
        return entity;
    }
}
