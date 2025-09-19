package api.karbonzeromovil.Config.Cors;

import api.karbonzeromovil.Services.Company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig {
    @Autowired
    private CompanyService companyService;

    private CorsConfiguration config;

    @Bean
    public CorsFilter corsFilter () {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        config = new CorsConfiguration();

        // Configuración esencial para el FrontEnd
        config.setAllowCredentials(true);
        reloadDomains();

        // Métodos permitidos
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");

        // Cabeceras permitidas
        config.addAllowedHeader("Origin");
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("Accept");
        config.addAllowedHeader("Authorization");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    public void reloadDomains () {
        config.setAllowedOrigins(new ArrayList<>());

        config.addAllowedOrigin("http://localhost:5173"); // Para desarrollo
        for (String domain : companyService.getAllCompaniesDomains()) {
            config.addAllowedOrigin(domain);
        }
    }
}
