package api.karbonzeromovil.Entities.Car;

import api.karbonzeromovil.Entities.User.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDUSER",nullable = false, referencedColumnName = "IDUSER")
    @JsonIgnore
    private UserEntity idUser;

    @ManyToOne
    @JoinColumn(name = "IDCARSIZE",nullable = false, referencedColumnName = "IDCARSIZE")
    private CarSizeEntity idCarSize;

    @OneToMany(mappedBy = "idCar")
    private List<TypeCarEntity> TypeCarList;
}
