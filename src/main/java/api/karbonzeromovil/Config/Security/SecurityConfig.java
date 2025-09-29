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
                            .requestMatchers(HttpMethod.GET, "/apiMap/getCountryGeo/{name}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/apiTransport/getTransportConsumptionsCO2WithLocation").permitAll()
                            .requestMatchers(HttpMethod.GET, "/apiService/getServiceConsumptionsCO2WithLocation").permitAll()
                            .requestMatchers(HttpMethod.GET, "/apiCar/getFuelConsumptionsCO2WithLocation").permitAll()
                            .requestMatchers(HttpMethod.GET, "apiFood/getFoodConsumptionsCO2WithLocation").permitAll()
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllUsers").hasAnyAuthority("ROL_0", "ROL_1")
                            .requestMatchers(HttpMethod.GET, "/apiUser/getAllUsersWithStatus/{status}").hasAnyAuthority("ROL_1")
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
