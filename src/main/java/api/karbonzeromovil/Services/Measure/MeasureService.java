package api.karbonzeromovil.Services.Measure;

import api.karbonzeromovil.Entities.Measure.MeasureEntity;
import api.karbonzeromovil.Entities.Measure.MeasureUnitEntity;
import api.karbonzeromovil.Models.DTO.Measure.MeasureDTO;
import api.karbonzeromovil.Models.DTO.Measure.MeasureUnitDTO;
import api.karbonzeromovil.Repositories.Measure.MeasureRepository;
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
public class MeasureService {
    @Autowired
    private MeasureRepository repo;

    public Page<MeasureDTO> getAllMeasures (int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<MeasureEntity> entity = repo.findAll(pageable);
        return entity.map(this::MeasureToDTO);
    }

    public MeasureDTO insertMeasure(MeasureDTO dto){
        if(dto == null){
            throw new IllegalArgumentException("Measure can't be null or empty");
        }
        try{
            MeasureEntity entity = DTOToMeasure(dto);
            MeasureEntity savedEntity = repo.save(entity);
            return MeasureToDTO(savedEntity);
        }
        catch (Exception e){
            throw new RuntimeException("Can't create Measure " + e.getMessage());
        }
    }

    public MeasureDTO updateMeasure(String id, MeasureDTO dto){
        try{
            if(repo.existsById(id)){
                MeasureEntity entity =  repo.getById(id);
                entity.setNameMeasure(dto.getNameMeasure());
                MeasureEntity savedEntity = repo.save(entity);
                return MeasureToDTO(savedEntity);
            }
            throw new IllegalArgumentException("The measure with the id: " + id +" doesn't exist");
        }
        catch(Exception e){
            throw new RuntimeException("Can't update measure" + e.getMessage());
        }
    }
    public boolean deleteMeasure(String id){
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
            throw new EmptyResultDataAccessException("The Measure with the id: " + id +" doesn't exist",1);
        }
    }
    private MeasureDTO MeasureToDTO(MeasureEntity entity){
        MeasureDTO dto = new MeasureDTO();
        dto.setIdMeasure(entity.getIdMeasure());
        dto.setNameMeasure(entity.getNameMeasure());
        return dto;
    }
    private MeasureEntity DTOToMeasure(MeasureDTO dto){
        MeasureEntity entity = new MeasureEntity();
        entity.setIdMeasure(dto.getIdMeasure());
        entity.setNameMeasure(dto.getNameMeasure());
        return entity;
    }

}
