package api.karbonzeromovil.Entities.Car;

import api.karbonzeromovil.Entities.Measure.MeasureEntity;
import api.karbonzeromovil.Entities.Measure.MeasureUnitEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "TBFUELTYPES")
@Setter @Getter @EqualsAndHashCode
public class FuelTypeEntity {

    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDFUELTYPE")
    private String idFuelType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDMEASUREUNIT", nullable = true, referencedColumnName = "IDMEASUREUNIT")
    @JsonIgnore
    private MeasureUnitEntity idMeasureUnit;

    @Column(name = "NAME", nullable = true, unique = true)
    private String name;

    @Column(name = "CARBONFOOTPRINT", nullable = true)
    private Double carbonFooTprint;

    @OneToMany(mappedBy = "idFuelType")
    private List<TypeCarEntity> typeCarList;
}
