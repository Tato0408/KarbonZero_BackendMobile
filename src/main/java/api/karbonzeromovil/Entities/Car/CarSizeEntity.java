package api.karbonzeromovil.Entities.Car;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "TBCARSIZES")
@Setter @Getter @EqualsAndHashCode

public class CarSizeEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDCARSIZE")
    private String idCarSize;
    @Column(name = "NAME", nullable = true, unique = true)
    private String name;

    @OneToMany(mappedBy = "idCarSize")
    private List<CarEntity> carSizeList;
}
