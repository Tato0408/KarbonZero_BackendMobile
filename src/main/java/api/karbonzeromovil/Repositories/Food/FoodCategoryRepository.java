package api.karbonzeromovil.Repositories.Food;

import api.karbonzeromovil.Entities.Food.FoodCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategoryEntity, String > {
    //Método de consulta derivada de JPA. Con propósito de búsqueda de una entidad
    //en la base de datos basándose en el valor de "foodCategoryName" de esa entidad.

    //El nombre del método y del parámetro debe de ser el mismo que el nombre del atributo en
    //el campo de la entidad, ya que esto indicara que la búsqueda se realizara sobre la propiedad
    //Básicamente la combinación del findBy y el nombre de la propiedad de la entidad le dice a
    //JPA qué columna buscar y con qué valor compararla sin tener que escribirla en SQL.
    Optional<FoodCategoryEntity> findByFoodCategoryName(String foodCategoryName);
}
