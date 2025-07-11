package api.karbonzeromovil.Entities.Service;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TBSERVICES")
@Getter @Setter @ToString
@EqualsAndHashCode
public class ServiceEntity {
    @Id
    @GenericGenerator(name = "db-uuid", strategy = "guid")
    @GeneratedValue(generator = "db-uuid")
    @Column(name="IDSERVICE")
    private String idService;
    @Column(name = "NAME",nullable = false,unique = true,length = 200)
    private String name;
}
