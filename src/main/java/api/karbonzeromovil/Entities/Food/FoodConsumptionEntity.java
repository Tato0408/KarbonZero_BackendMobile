package api.karbonzeromovil.Entities.Food;

import api.karbonzeromovil.Entities.User.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name ="TBFOODCONSUMPTIONS")
@Getter @Setter
@ToString   @EqualsAndHashCode
public class FoodConsumptionEntity {
    @Id
    @GenericGenerator(name="db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDFOODCONSUMPTION")
    private String idFoodConsumption;

    @ManyToOne
    @JoinColumn(name="IDUSER", nullable = false, referencedColumnName = "IDUSER")
    private UserEntity idUser;

    @Column(name="CONSUMPTIONDATE")
    private LocalDateTime consumptionDate;

    @Column(name="LATITUDE")
    private Double latitude;

    @Column(name="LONGITUDE")
    private Double longitude;

    @OneToMany(mappedBy = "foodConsumption", cascade = CascadeType.ALL)
    private List<FoodConsumptionFoodEntity> foodConsumptionFood = new ArrayList<>();
}
