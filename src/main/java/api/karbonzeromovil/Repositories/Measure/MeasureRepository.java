package api.karbonzeromovil.Repositories.Measure;

import api.karbonzeromovil.Entities.Measure.MeasureEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureRepository extends JpaRepository<MeasureEntity, String> {
    Page<MeasureEntity> findAll (Pageable pagebale);
    Page<MeasureEntity> findByNameMeasure(String nameMeasure, Pageable pageable);
}
