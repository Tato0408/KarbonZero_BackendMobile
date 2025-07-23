package api.karbonzeromovil.Services.Company;

import api.karbonzeromovil.Entities.Company.CompanyEntity;
import api.karbonzeromovil.Models.DTO.Company.CompanyDTO;
import api.karbonzeromovil.Repositories.Company.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service

public class CompanyService {

    @Autowired
    private CompanyRepository repo;

    public List<CompanyDTO> getAllCompany(){
        List<CompanyEntity> company = repo.findAll();
        return company.stream()
                .map(this::CompanyToDTO)
                .collect(Collectors.toList());
    }

    public CompanyDTO insertCompany(CompanyDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Company cannot be null or empty");
        }
        try {
            CompanyEntity entity = DT0ToCompany(dto);
            CompanyEntity savedCar = repo.save(entity);
            return CompanyToDTO(savedCar);
        } catch (Exception e) {
            throw new RuntimeException("The company could not be created"+ e.getMessage());
        }
    }


    public CompanyDTO updateCompany(String id, CompanyDTO dto){
        try {
            if (repo.existsById((id))){
                CompanyEntity entity = repo.getById(id);
                entity.setName(dto.getName());
                entity.setDomain(dto.getDomain());

                CompanyEntity savedCompany = repo.save(entity);
                return CompanyToDTO(savedCompany);
            }
            throw new IllegalArgumentException("The company could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The company could not be updated" + e.getMessage());
        }
    }

    public boolean deleteCompany(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The Company with the ID : " + id + " doesn't exists", 1);
        }
    }


    private CompanyDTO CompanyToDTO(CompanyEntity entity){
        CompanyDTO dto = new CompanyDTO();
        dto.setIdCompany(entity.getIdCompany());
        dto.setName(entity.getName());
        dto.setDomain(entity.getDomain());
        return dto;
    }
    private CompanyEntity DT0ToCompany(CompanyDTO dto){
        CompanyEntity entity = new CompanyEntity();
        entity.setIdCompany(dto.getIdCompany());
        entity.setName(dto.getName());
        entity.setDomain(dto.getDomain());
        return entity;
    }
}
