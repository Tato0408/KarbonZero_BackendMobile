package api.karbonzeromovil.Entities.Service;

import api.karbonzeromovil.Entities.User.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBSERVICECONSUMPTIONS")
@Getter @Setter @ToString @EqualsAndHashCode
public class ServiceConsumptionEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDSERVICECONSUMPTION")
    private String idServiceConsumption;
    @Column(name = "IDMEASUREUNITSERVICE")
    private String idMeasureUnitService;
    @ManyToOne
    @JoinColumn(name = "IDUSER")
    private UserEntity idUser;
    @Column(name = "QUANTITY")
    private Number quantity;
    @Column(name = "CONSUMPTIONDATE")
    private LocalDateTime consumptionDate;
    @Column(name = "LATITUDE")
    private Double latitude;
    @Column(name = "LONGITUDE")
    private Double longitude;
}
