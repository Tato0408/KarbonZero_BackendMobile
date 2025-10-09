package api.karbonzeromovil.Config.Cors;

import api.karbonzeromovil.Services.Company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {
    @Autowired
    private CompanyService companyService;

    public CorsConfiguration config;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        config = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Configuración esencial para el FrontEnd
        config.setAllowCredentials(true);
        reloadDomains();

        // Métodos permitidos
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Disposition", "Set-Cookie"));
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return source;
    }

    public void reloadDomains () {
        config.setAllowedOrigins(new ArrayList<>());

        config.setAllowedOriginPatterns(Arrays.asList( // Para el desarrollo
                "http://localhost:*", // Cubre todos los puertos en localhost
                "http://127.0.0.1:*"  // También la IP directa
        ));

        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://localhost:5174");


        for (String domain : companyService.getAllCompaniesDomains()) {
            config.addAllowedOrigin(domain);
        }
    }
}
