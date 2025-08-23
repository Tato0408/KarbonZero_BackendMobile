package api.karbonzeromovil.Entities.Car;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "TBTYPESCARS")
@Setter @Getter @EqualsAndHashCode

public class TypeCarEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDTYPECAR")
    private String idTypeCar;

    @ManyToOne
    @JoinColumn(name = "IDCAR", nullable = true, referencedColumnName = "IDCAR")
    private CarEntity idCar;

    @ManyToOne
    @JoinColumn(name = "IDFUELTYPE", nullable = true)
    private FuelTypeEntity idFuelType;

    @OneToMany(mappedBy = "idTypeCar")
    private List<FuelConsumptionEntity> consumptionList;
}
