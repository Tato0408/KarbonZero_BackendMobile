package api.karbonzeromovil.Services.Measure;

import api.karbonzeromovil.Entities.Measure.MeasureUnitEntity;
import api.karbonzeromovil.Models.DTO.Measure.MeasureUnitDTO;
import api.karbonzeromovil.Repositories.Measure.MeasureRepository;
import api.karbonzeromovil.Repositories.Measure.MeasureUnitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MeasureUnitService {
    @Autowired
    private MeasureUnitRepository repo;
    @Autowired
    private MeasureRepository measureRepo;

    public List<MeasureUnitDTO> getAllMeasureUnit (){
        List<MeasureUnitEntity> measureUnit = repo.findAll();
        return measureUnit.stream()
                .map(this::MeasureUnitToDTO)
                .collect(Collectors.toList());
    }

    public MeasureUnitDTO insertMeasureUnit(MeasureUnitDTO dto){
        if(dto == null){
            throw new IllegalArgumentException("Measure unit can't be null or empty");
        }
        try{
            MeasureUnitEntity entity = DTOToMeasureUnit(dto);
            MeasureUnitEntity savedEntity = repo.save(entity);
            return MeasureUnitToDTO(savedEntity);
        }
        catch (Exception e){
            throw new RuntimeException("Can't create Measure unit" + e.getMessage());
        }
    }
    public MeasureUnitDTO updateMeasureUnit(String id, MeasureUnitDTO dto){
        try{
            if(repo.existsById(id)){
                MeasureUnitEntity entity =  repo.getById(id);
                entity.setNameMeasuresUnit(dto.getNameMeasureUnit());
                entity.setIdMeasure(measureRepo.getReferenceById(dto.getIdMeasure()));
                MeasureUnitEntity savedEntity = repo.save(entity);
                return MeasureUnitToDTO(savedEntity);
            }
            throw new IllegalArgumentException("The measure unit with the id: " + id +" doesn't exist");
        }
        catch(Exception e){
            throw new RuntimeException("Can't update measure unit" + e.getMessage());
        }
    }
    public boolean deleteMeasureUnit(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }
            else{
                return false;
            }
        }
        catch(EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The Measure unit with the id: " + id +" doesn't exist",1);
        }
    }


    private MeasureUnitDTO MeasureUnitToDTO(MeasureUnitEntity entity){
        MeasureUnitDTO dto = new MeasureUnitDTO();
        dto.setIdMeasureUnit(entity.getIdMeasureUnit());
        dto.setIdMeasure(entity.getIdMeasure().getIdMeasure());
        dto.setNameMeasureUnit(entity.getNameMeasuresUnit());

        return dto;
    }
    private MeasureUnitEntity DTOToMeasureUnit(MeasureUnitDTO dto){
        MeasureUnitEntity entity = new MeasureUnitEntity();
        entity.setIdMeasureUnit(dto.getIdMeasureUnit());
        entity.setIdMeasure(measureRepo.getReferenceById(dto.getIdMeasure()));
        entity.setNameMeasuresUnit(dto.getNameMeasureUnit());
        return entity;
    }

}
