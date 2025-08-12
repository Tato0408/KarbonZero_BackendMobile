package api.karbonzeromovil.Services.User;

import api.karbonzeromovil.Entities.User.CountryEntity;
import api.karbonzeromovil.Models.DTO.User.CountryDTO;
import api.karbonzeromovil.Repositories.User.CountryRepository;
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
public class CountryService {
    @Autowired
    private CountryRepository repo;

    public Page<CountryDTO> getAllCountries(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<CountryEntity> countries = repo.findAll(pageable);
        return countries.map(this::CountryToDTO);
    }
    public CountryDTO insertCountry(CountryDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Country cannot be null or empty");
        }
        try {
            CountryEntity entity = DTOToCountry(dto);
            CountryEntity savedCountry = repo.save(entity);
            return CountryToDTO(savedCountry);
        } catch (Exception e) {
            throw new RuntimeException("The country could not be created"+ e.getMessage());
        }
    }
    public CountryDTO updateCountry(String id, CountryDTO dto){
        try {
            if (repo.existsById((id))){
                CountryEntity entity = repo.getReferenceById(id);
                entity.setName(dto.getName());
                entity.setCarbonFootprint(dto.getCarbonFootprint());

                CountryEntity savedCountry = repo.save(entity);
                return CountryToDTO(savedCountry);
            }
            throw new IllegalArgumentException("The country could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The country could not be updated" + e.getMessage());
        }
    }
    public boolean deleteCountry(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The Country with the ID : " + id + " doesn't exists", 1);
        }
    }
    private CountryDTO CountryToDTO(CountryEntity entity){
        CountryDTO dto = new CountryDTO();
        dto.setIdCountry(entity.getIdCountry());
        dto.setName(entity.getName());
        dto.setCarbonFootprint(entity.getCarbonFootprint());
        return dto;
    }
    private CountryEntity DTOToCountry(CountryDTO dto){
        CountryEntity entity = new CountryEntity();
        entity.setIdCountry(dto.getIdCountry());
        entity.setName(dto.getName());
        entity.setCarbonFootprint(dto.getCarbonFootprint());
        return entity;
    }

}
