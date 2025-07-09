package api.karbonzeromovil.Entities.Food;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Entity
@Table(name="TBFOODCATEGORIES")
@ToString @EqualsAndHashCode
@Getter @Setter
public class FoodCategoryEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDFOODCATEGORY")
    private String idFoodCategory;

    @Column(name="NAME")
    private String foodCategoryName;

    @Column(name="CARBONFOOTPRINT")
    private double carbonFootPrint;

    @OneToMany(mappedBy = "idFoodCategory")
    private Set<FoodEntity> foodSet;
}
