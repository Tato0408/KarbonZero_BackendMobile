package api.karbonzeromovil.Services.Car;

import api.karbonzeromovil.Entities.Car.*;
import api.karbonzeromovil.Entities.User.UserEntity;
import api.karbonzeromovil.Models.DTO.Car.CarDTO;
import api.karbonzeromovil.Models.DTO.Car.FuelConsumptionDTO;
import api.karbonzeromovil.Models.DTO.Car.TypeCarDTO;
import api.karbonzeromovil.Repositories.Car.CarRepository;
import api.karbonzeromovil.Repositories.Car.FuelTypeRepository;
import api.karbonzeromovil.Repositories.Car.TypeCarRepository;
import api.karbonzeromovil.Repositories.User.UserRepository;
import api.karbonzeromovil.Services.User.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service

public class TypeCarService {
    @Autowired
    private TypeCarRepository repo;

    @Autowired
    private  UserRepository userRepo;
    @Autowired
    private CarRepository CarRepo;

    @Autowired
    private FuelTypeRepository FuelTypeRepo;

    @Autowired
    private CarService carService;


    @Autowired
    private UserService userService;
    public List<TypeCarDTO> getAllTypeCar(){
        List<TypeCarEntity> cartype = repo.findAll();
        return cartype.stream()
                .map(this::TypeCarToDTO)
                .collect(Collectors.toList());
    }

    public TypeCarDTO getTypeCarById(String id){
        if(repo.existsById(id)){
            TypeCarEntity entity = repo.getReferenceById(id);
            return TypeCarToDTO(entity);
        }
        return null;
    }

    public TypeCarDTO insertTypeCar(TypeCarDTO dto) throws IllegalAccessException {
        if(dto == null){
            throw new IllegalAccessException("Car type cannot be null or empty");
        }
        try {
            TypeCarEntity entity = DT0ToTypeCar(dto);
            TypeCarEntity savedTypeCar = repo.save(entity);
            return TypeCarToDTO(savedTypeCar);
        } catch (Exception e) {
            throw new RuntimeException("The car type could not be created"+ e.getMessage());
        }
    }

    public TypeCarDTO updateTypeCar(String id, TypeCarDTO dto){
        try {
            if (repo.existsById((id))){
                TypeCarEntity entity = repo.getById(id);
                entity.setIdCar(CarRepo.getReferenceById(dto.getIdCar()));
                entity.setIdFuelType(FuelTypeRepo.getReferenceById(dto.getIdFuelType()));

                TypeCarEntity savedTypeCar = repo.save(entity);
                return TypeCarToDTO(savedTypeCar);
            }
            throw new IllegalArgumentException("The car type could not be updated"+id+" check if it exist");
        }catch (Exception e){
            throw new RuntimeException("The car type could not be updated" + e.getMessage());
        }
    }

    public HashMap<String, Double> getAllCarsC02ByUserCar(String idUser){
        /// Aca definimos el hashMap que vamos a retornar
        HashMap<String, Double> hashMap = new HashMap<>();
        /// Obtenemos la referencia de nuestro idUser para obtener ese usuario
        UserEntity  userEntity = userRepo.getReferenceById(idUser);
        /// Iniciamos nuestro valor de el hashmap con esa clave, puesto que cabal esa es la que cambiamos
        hashMap.put(userEntity.getIdUser(), 0.00);
        /// Obtenemos todos los carros de x usuario
        List<CarEntity> cars = userEntity.getGetAllUserCars();
        /// Iteramos sobre esos carros
        cars.forEach(car->{
            /// Obtenemos la referencia de ese car entity al cual vamos a extraer su fokin huella de carbono
            CarEntity carEntity = CarRepo.getReferenceById(car.getIdCar());
            /// Ahora obtenemos todos los tipos de carro al cual esta asociado ese carro
            List<TypeCarEntity> typeCars = carEntity.getTypeCarList();
            /// Iteramos por todos los tipos de carro al cual este asociado ese usuario
            typeCars.forEach(typeCar ->{
                /// Aca obtenemos el fuelType al cual esta asociado ese typeCar
                FuelTypeEntity fuelType = typeCar.getIdFuelType();
                /// Y aca obtenemos todos los consumos de ese tipo de carro
                List<FuelConsumptionEntity> consumptions = typeCar.getFuelConsumptionList();
                /// Ahora iteramos sobre esos consumos encontrados
                consumptions.forEach(consumption ->{
                    /// Agregamos a esa fila del hashMap que tiene el idUser que definimos al inicio, su consumo de huella de carbono
                    hashMap.put(userEntity.getIdUser(),
                            (consumption.getQuantity() * fuelType.getCarbonFooTprint()) +
                                    hashMap.get(userEntity.getIdUser()));
                });
            });
        });
        return hashMap;
    }

    public List<FuelConsumptionDTO> getAllFuelConsumptionsByTypeCar(String idCar){
        TypeCarEntity typeCarEntity = repo.getReferenceById(idCar);
        return typeCarEntity.getFuelConsumptionList().stream().map(new FuelConsumptionService()::FuelConsumptionToDTO).collect(Collectors.toList());
    }

    public boolean deleteTypeCar(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The Car type with the ID : " + id + " doesn't exists", 1);
        }
    }

    public TypeCarDTO TypeCarToDTO(TypeCarEntity entity){
        TypeCarDTO dto = new TypeCarDTO();
        dto.setIdTypeCar(entity.getIdTypeCar());
        if(entity.getIdCar() != null && entity.getIdFuelType() != null){
            dto.setIdCar(entity.getIdCar().getIdCar());
            dto.setIdFuelType(entity.getIdFuelType().getIdFuelType());
            dto.setSizeCar(entity.getIdCar().getIdCarSize().getName());
            dto.setUserName(entity.getIdCar().getIdUser().getUserName());
            dto.setCarbonBootprint(entity.getIdFuelType().getCarbonFooTprint());
            dto.setFuelTypeName(entity.getIdFuelType().getName());
        }
        return dto;
    }
    private TypeCarEntity DT0ToTypeCar(TypeCarDTO dto){
        TypeCarEntity entity = new TypeCarEntity();
        entity.setIdTypeCar(entity.getIdTypeCar());
        entity.setIdCar(CarRepo.getReferenceById(dto.getIdCar()));
        entity.setIdFuelType(FuelTypeRepo.getReferenceById(dto.getIdFuelType()));
        return entity;
    }
}
