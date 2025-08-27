package api.karbonzeromovil.Repositories.Food;

import api.karbonzeromovil.Entities.Food.FoodCategoryEntity;
import api.karbonzeromovil.Entities.Food.FoodConsumptionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodConsumptionRepository extends JpaRepository<FoodConsumptionEntity, String> {
    Page<FoodConsumptionEntity> findAll (Pageable pagebale);
    int countByIdUser_IdUser(String userId);

//    @Query("""
//        select coalesce(sum(o.quantity), 0)
//        from FoodConsumptionEntity o
//        where o.user.idUser = :userId
//    """)
//    Double sumTotalByUser(@Param("userId") String userId);

}
