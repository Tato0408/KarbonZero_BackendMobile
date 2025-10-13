package api.karbonzeromovil.Config.Security;

import api.karbonzeromovil.Utils.JwtCookieAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtCookieAuthFilter jwtCookieAuthFilter;
    private final CorsConfigurationSource corsConfigurationSource; // Inyecta CorsConfigurationSource

    public SecurityConfig(JwtCookieAuthFilter jwtCookieAuthFilter,
                          CorsConfigurationSource corsConfigurationSource) {
        this.jwtCookieAuthFilter = jwtCookieAuthFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) {
        try {
            http
                    .csrf(csrf -> csrf.disable())
                    .cors(cors -> cors.configurationSource(corsConfigurationSource)) // usa tu bean
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // ← Permite preflight requests
                            .requestMatchers(HttpMethod.POST, "/apiAuth/login").permitAll()
                            .requestMatchers(HttpMethod.POST, "/apiAuth/logout").permitAll()
                            .requestMatchers(HttpMethod.POST, "/apiAuth/authenticateCompany").permitAll()
                            .requestMatchers(HttpMethod.GET, "/apiAuth/me").authenticated()
                            .requestMatchers(HttpMethod.GET, "/apiAuth/company").hasAnyAuthority("ROL_2")

                            .requestMatchers(HttpMethod.POST, "/apiUser/confirmPassword").permitAll()
                            .requestMatchers(HttpMethod.POST, "/apiAuth/forgotPassword").permitAll()
                            .requestMatchers(HttpMethod.POST, "/apiAuth/verifyCode").permitAll()

                            .requestMatchers(HttpMethod.GET, "/apiMap/getCountryGeo/{name}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/apiTransport/getTransportConsumptionsCO2WithLocation").permitAll()
                            .requestMatchers(HttpMethod.GET, "/apiService/getServiceConsumptionsCO2WithLocation").permitAll()
                            .requestMatchers(HttpMethod.GET, "/apiCar/getFuelConsumptionsCO2WithLocation").permitAll()
                            .requestMatchers(HttpMethod.GET, "/apiFood/getFoodConsumptionsCO2WithLocation").permitAll()

                            // API CAR
                            .requestMatchers(HttpMethod.GET, "/apiCar/getFuelTypeByName/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getCarSizeByName/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getAllCar").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getAllCarSize").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getCarSizeSp").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getAllTypeCar").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getAllFuelType").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getAllFuelTypeSP").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getAllFuelConsumption").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getCarById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getCarSizeById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getTypeCarById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getFuelTypeById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getFuelConsumptionById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getFuelConsumptionsCO2WithLocation").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getAllTypeCarsByCar/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getAllFuelConsumptionsByTypeCar/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCar/getAllC02CarsByUser/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.POST, "/apiCar/insertCar").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiCar/insertCarSize").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiCar/insertTypeCar").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiCar/insertFuelType").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiCar/insertFuelConsumption").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.PUT, "/apiCar/updateCar/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiCar/updateCarSize/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiCar/updateTypeCar/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiCar/updateFuelType/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiCar/updateFuelConsumption/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.DELETE, "/apiCar/deleteCar/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiCar/deleteCarSize/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiCar/deleteTypeCar/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiCar/deleteFuelType/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiCar/deleteFuelConsumption/**").hasAnyAuthority("ROL_0", "ROL_1")

                            // API CLOUDINARY
                            .requestMatchers(HttpMethod.POST, "/api/image/upload").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/api/image/upload-to-folder").hasAnyAuthority("ROL_0", "ROL_1")

                            // API COMPANY
                            .requestMatchers(HttpMethod.GET, "/apiCompany/getCompanyByName/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCompany/getAllChannelPost/**").hasAnyAuthority("ROL_2")
                            .requestMatchers(HttpMethod.GET, "/apiCompany/getChannelPostAproved/**").hasAnyAuthority("ROL_2")
                            .requestMatchers(HttpMethod.GET, "/apiCompany/getChannelPostNoAproved/**").hasAnyAuthority("ROL_2")
                            .requestMatchers(HttpMethod.GET, "/apiCompany/getAllCompany").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCompany/getAllCompaniesSP").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCompany/getAllCompaniesDomains").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCompany/getAllChannel").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCompany/getAllChannelUser").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCompany/getCompanyById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCompany/getChannelById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCompany/getChannelUserById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCompany/getUserChannelRelation/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiCompany/getPostByChannel/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.POST, "/apiCompany/insertCompany").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiCompany/insertChannel").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiCompany/insertChannelUser").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiCompany/insertChannelPost").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.PUT, "/apiCompany/approvePost/**").hasAnyAuthority("ROL_2")
                            .requestMatchers(HttpMethod.PUT, "/apiCompany/updateChannelPost/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiCompany/updateCompany/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiCompany/updateChannel/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiCompany/updateChannelByApiKey/**").hasAnyAuthority("ROL_2")
                            .requestMatchers(HttpMethod.PUT, "/apiCompany/updateChannelUser/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.DELETE, "/apiCompany/deleteCompany/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiCompany/deleteChannel/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiCompany/deleteChannelUser/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiCompany/deleteChannelPost/**").hasAnyAuthority("ROL_2")

                            // API FOOD

                            .requestMatchers(HttpMethod.GET, "/apiFood/getAllFoods").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiFood/getAllFoodCategoriesPagination").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiFood/getAllFoodCategories").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiFood/getAllFoodConsumptions").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiFood/getAllFoodConsumptionFoods").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiFood/getFoodById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiFood/getFoodCategoryById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiFood/getFoodConsumptionById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiFood/getFoodConsumptionFoodById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiFood/getAllfoodConsumptionFoodByFoodConsumption/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiFood/getFoodConsumptionsCO2WithLocation").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiFood/getFoodByFoodName/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiFood/getFoodCategoryByFoodCategoryName/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiFood/getAllC02FoodByUser/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.POST, "/apiFood/insertFood").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiFood/insertFoodCategory").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiFood/insertFoodConsumption").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiFood/insertFoodConsumptionFood").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.PUT, "/apiFood/updateFood/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiFood/updateFoodCategory/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiFood/updateFoodConsumption/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiFood/updateFoodConsumptionFood/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.DELETE, "/apiFood/deleteFood/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiFood/deleteFoodCategory/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiFood/deleteFoodConsumption/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiFood/deleteFoodConsumptionFood/**").hasAnyAuthority("ROL_0", "ROL_1")

                            // API LIVING SPACE
                            .requestMatchers(HttpMethod.GET, "/apiLivingSpace/getAllLivingSpaceTypes").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiLivingSpace/getAllLivingSpaces").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiLivingSpace/getLivingSpaceById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiLivingSpace/getLivingSpaceTypeById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiLivingSpace/getLivingSpaceTypeByName/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.POST, "/apiLivingSpace/insertLivingSpaceType").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiLivingSpace/insertLivingSpace").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.PUT, "/apiLivingSpace/updateLivingSpaceType/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiLivingSpace/updateLivingSpace/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.DELETE, "/apiLivingSpace/deleteLivingSpaceType/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiLivingSpace/deleteLivingSpace/**").hasAnyAuthority("ROL_0", "ROL_1")

                            // API MEASURE
                            .requestMatchers(HttpMethod.GET, "/apiMeasure/getAllMeasureUnit").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiMeasure/getAllMeasures").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiMeasure/getAllMeasureUnitList").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiMeasure/getMeasureById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiMeasure/getMeasureUnitById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiMeasure/getMeasureUnitByName/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiMeasure/getMeasureByName/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.POST, "/apiMeasure/insertMeasureUnit").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiMeasure/insertMeasure").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.PUT, "/apiMeasure/updateMeasureUnit/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiMeasure/updateMeasure/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.DELETE, "/apiMeasure/deleteMeasureUnit/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiMeasure/deleteMeasure/**").hasAnyAuthority("ROL_0", "ROL_1")

                            // API PET
                            .requestMatchers(HttpMethod.GET, "/apiPet/getAllUserPets").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiPet/getAllPetType").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiPet/getUserPetById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiPet/getPetTypeById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiPet/getPetTypeByName/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.POST, "/apiPet/insertUserPet").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiPet/insertPetType").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.PUT, "/apiPet/updateUserPet/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiPet/updatePetType/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.DELETE, "/apiPet/deleteUserPet/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiPet/deletePetType/**").hasAnyAuthority("ROL_0", "ROL_1")

                            // API SERVICE
                            .requestMatchers(HttpMethod.GET, "/apiService/getAllServices").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiService/getAllC02ServiceByUser/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiService/getMeasureUnitService").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiService/getServiceConsumption").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiService/getServiceByName/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiService/getMeasureUnitServiceByName/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiService/getAllServiceList").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiService/getServiceById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiService/getMeasureUnitServiceById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiService/getServiceConsumptionById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiService/getServiceConsumptionsCO2WithLocation").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.POST, "/apiService/insertService").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiService/insertMeasureUnitService").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiService/insertServiceConsumption").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.PUT, "/apiService/updateService/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiService/updateMeasureUnitService/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiService/updateServiceConsumption/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.DELETE, "/apiService/deleteService/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiService/deleteMeasureUnitService/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiService/deleteServiceConsumption/**").hasAnyAuthority("ROL_0", "ROL_1")

                            // API TRANSPORT
                            .requestMatchers(HttpMethod.GET, "/apiTransport/getAllTransports").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiTransport/getTransportConsumption").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiTransport/getTransportConsumptionsCO2WithLocation").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiTransport/getAllC02TransportByUser/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiTransport/getTransportByName/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiTransport/getTransportById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiTransport/getTransportConsumptionById/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.POST, "/apiTransport/insertTransport").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiTransport/insertTransportConsumption").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.PUT, "/apiTransport/updateTransport/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiTransport/updateTransportConsumption/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.DELETE, "/apiTransport/deleteTransport/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiTransport/deleteTransportConsumption/**").hasAnyAuthority("ROL_0", "ROL_1")

                            // API USER
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllUsers").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllUsersWithStatus/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getUserById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAdminById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getUsersByName/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAdminsByName/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllFriends").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getFriendById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllAchievements").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAchievementById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllCountries").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllCountriesSP").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getCountryById/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getCountryByName/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getCountAllFollowersByUser/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/searchUsers/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getCountAllFollowingsByUser/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllAchievementsByUser/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllCarsByUser/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getFriendsByMainUser/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllChannelsByUser/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getFriendsBySecondaryUser/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllUserLivingConsumptions/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllTransportConsumptionsByUser/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllUserFoodConsumptions/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllPetsByUser/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getExistRelation/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getRelation/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getCountUserConsumptions/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllUserServiceConsumption/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.POST, "/apiUser/insertUser").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiUser/updateDisabled/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiUser/insertFriend").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiUser/insertAchievement").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiUser/insertCountry").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.POST, "/apiUser/confirmPassword").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.PUT, "/apiUser/updateUser/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiUser/updateFriend/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiUser/updateAchievement/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.PUT, "/apiUser/updateCountry/**").hasAnyAuthority("ROL_0", "ROL_1")

                            .requestMatchers(HttpMethod.DELETE, "/apiUser/deleteUser/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiUser/deleteFriend/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiUser/deleteAchievement/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.DELETE, "/apiUser/deleteCountry/**").hasAnyAuthority("ROL_0", "ROL_1")
                            .anyRequest().authenticated())
            .sessionManagement(sess ->
                    sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtCookieAuthFilter, UsernamePasswordAuthenticationFilter.class);

            return  http.build();
        } catch (Exception e) {
            return null;
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        try {
            return config.getAuthenticationManager();
        } catch (Exception e) {
            return null;
        }
    }
}
