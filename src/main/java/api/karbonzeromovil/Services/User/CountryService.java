package api.karbonzeromovil.Services.User;

import api.karbonzeromovil.Entities.User.CountryEntity;
import api.karbonzeromovil.Models.DTO.User.CountryDTO;
import api.karbonzeromovil.Repositories.User.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CountryService {
    @Autowired
    private CountryRepository repo;

    public List<CountryDTO> getAllCountries(){
        List<CountryEntity> countries = repo.findAll();
        return countries.stream()
                .map(this::CountryToDTO)
                .collect(Collectors.toList());
    }
    public boolean deleteCountry(String id){
        try{
            System.out.println(id);
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
