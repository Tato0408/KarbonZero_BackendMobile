package api.karbonzeromovil.Entities.Car;

import api.karbonzeromovil.Entities.User.UserEntity;
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
    @Column(name = "IDCAR", nullable = true)
    private String idCar;
    @Column(name = "IDFUELTYPE", nullable = true)
    private String idFuelType;

    @OneToMany(mappedBy = "idTypeCar")
    private List<FuelConsumptionEntity> consumptionList;
}
