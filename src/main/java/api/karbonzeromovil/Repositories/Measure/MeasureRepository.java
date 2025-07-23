package api.karbonzeromovil.Repositories.Measure;

import api.karbonzeromovil.Entities.Measure.MeasureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureRepository extends JpaRepository<MeasureEntity, String> {
}
