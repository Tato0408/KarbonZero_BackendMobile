package api.karbonzeromovil.Entities.Car;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "TBCARS")
@Setter @Getter @EqualsAndHashCode

public class CarEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDCAR")
    private String idCar;
    @Column(name = "IDUSER",nullable = false)
    private String idUser;
    @Column(name = "IDCARSIZE",nullable = false)
    private String idCarSize;

    @OneToMany(mappedBy = "idCar")
    private List<TypeCarEntity> TypeCarList;
}
