package api.karbonzeromovil.Entities.Car;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "TBFUELCONSUMPTIONS")
@Setter @Getter @EqualsAndHashCode

public class FuelConsumptionEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")

    @Column(name="IDFUELCONSUMPTION")
    private String idFuelConsumption;

    @ManyToOne
    @JoinColumn(name = "IDTYPECAR", nullable = false, referencedColumnName = "IDTYPECAR")
    private TypeCarEntity idTypeCar;

    @Column(name = "QUANTITY", nullable = false)
    private Double quantity;

    @Column(name="CONSUMPTIONDATE", nullable = false)
    private Date consumsuptionDate;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;
}
