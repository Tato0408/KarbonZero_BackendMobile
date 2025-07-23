package api.karbonzeromovil.Entities.Measure;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TBMEASUREUNITS")
@ToString @EqualsAndHashCode
@Getter @Setter
public class MeasureUnitEntity {
    @Id
    @GenericGenerator(name="db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDMEASUREUNIT")
    private String idMeasureUnit;

    @ManyToOne
    @JoinColumn(name="IDMEASURE", nullable = false)
    private MeasureEntity idMeasure;

    @Column(name="NAME")
    private String nameMeasuresUnit;
}
