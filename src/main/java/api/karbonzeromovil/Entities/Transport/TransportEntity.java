package api.karbonzeromovil.Entities.Transport;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TBTRANSPORTS")
@Getter @Setter @ToString @EqualsAndHashCode
public class TransportEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDTRANSPORT")
    private String idTransport;
    @Column(name = "NAME",nullable = false,unique = true,length = 200)
    private String name;
    @Column(name = "CARBONFOOTPRINT", nullable = false)
    private Double carbonFootprint;
}
