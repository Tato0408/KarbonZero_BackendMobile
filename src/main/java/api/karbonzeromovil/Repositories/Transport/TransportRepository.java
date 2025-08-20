package api.karbonzeromovil.Repositories.Transport;

import api.karbonzeromovil.Entities.Transport.TransportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportRepository extends JpaRepository<TransportEntity,String> {
    Page<TransportEntity> findAll(Pageable pageable);
}
