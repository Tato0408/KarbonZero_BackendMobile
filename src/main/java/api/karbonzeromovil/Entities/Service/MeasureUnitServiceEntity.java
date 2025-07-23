package api.karbonzeromovil.Entities.Service;

import api.karbonzeromovil.Entities.Measure.MeasureUnitEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Entity
@Table(name="TBMEASUREUNITSSERVICES")
@Getter @Setter @ToString @EqualsAndHashCode
public class MeasureUnitServiceEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDMEASUREUNITSERVICE ")
    private String idMeasureUnitService;
    @ManyToOne
    @JoinColumn(name = "IDSERVICE")
    private ServiceEntity idService;
    @ManyToOne
    @JoinColumn(name = "IDMEASUREUNIT")
    private MeasureUnitEntity idMeasureUnit;
    @Column(name = "CARBONFOOTPRINT", nullable = false)
    private Double carbonFootprint;
}
