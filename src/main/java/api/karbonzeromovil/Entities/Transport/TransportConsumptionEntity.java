package api.karbonzeromovil.Entities.Transport;

import api.karbonzeromovil.Entities.User.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBTRANSPORTCONSUMPTION")
@Getter @Setter @ToString @EqualsAndHashCode
public class TransportConsumptionEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDTRANSPORTCONSUMPTION")
    private String idTransportConsumption;
    @ManyToOne
    @JoinColumn(name = "IDTRANSPORT")
    private TransportEntity idTransport;
    @ManyToOne
    @JoinColumn(name = "IDUSER")
    private UserEntity idUser;
    @Column(name = "DISTANCE")
    private Double distance;
    @Column(name = "CONSUMPTIONDATE")
    private LocalDateTime consumptionDate;
    @Column(name = "LATITUDE")
    private Double latitude;
    @Column(name = "LONGITUDE")
    private Double longitude;
}
