package api.karbonzeromovil.Repositories.Measure;

import api.karbonzeromovil.Entities.Measure.MeasureUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureUnitRepository extends JpaRepository<MeasureUnitEntity, String > {
}
