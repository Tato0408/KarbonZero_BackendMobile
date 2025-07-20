package api.karbonzeromovil.Entities.Measure;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name="TBMEASURES")
@ToString
@EqualsAndHashCode
@Getter @Setter
public class MeasureEntity {
    @Id
    @GenericGenerator(name="db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDMEASURE")
    private String idMeasure;

    @Column(name="NAME")
    private String nameMeasure;

    @OneToMany(mappedBy = "idMeasureUnit")
    private List<MeasureUnitEntity> MeasureUnitList;
}
