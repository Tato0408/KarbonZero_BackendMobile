package api.karbonzeromovil.Repositories.Service;

import api.karbonzeromovil.Entities.Service.MeasureUnitServiceEntity;
import api.karbonzeromovil.Entities.Service.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasureUnitServiceRepository extends JpaRepository<MeasureUnitServiceEntity, String> {
    Page<MeasureUnitServiceEntity> findAll(Pageable pageable);
    Page<MeasureUnitServiceEntity> findByIdServiceIn (List<ServiceEntity> idService, Pageable pageable);
}
