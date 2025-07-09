package api.karbonzeromovil.Entities.Food;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Entity
@Table(name = "TBFOODS")
@Getter @Setter
@ToString @EqualsAndHashCode
public class FoodEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name = "IDFOOD")
    private String idFood;

    @ManyToOne
    @JoinColumn(name ="IDFOODCATEGORY", nullable = false)
    private String idFoodCategory;

    @Column(name = "NAME")
    private String foodName;

    @OneToMany(mappedBy = "idFood")
    private Set<FoodConsumptionFoodEntity> foodConsumptionFoodSet;
}
