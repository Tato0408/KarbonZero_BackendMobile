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

    private CorsConfiguration config;
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Configuración esencial
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost");
        config.addAllowedOrigin("https://localhost/");
        config.addAllowedOrigin("http://localhost:3000"); // React
        config.addAllowedOrigin("http://localhost:8080"); // Vue
        config.addAllowedOrigin("http://localhost:4200"); // Angular
        config.addAllowedOrigin("https://learn-api-steel.vercel.app/");
        config.addAllowedOrigin("https://karbon-zero-front-end.vercel.app/");
        config.addAllowedOrigin("https://karbon-zero-managment-front-end-teal.vercel.app");


        // Métodos HTTP permitidos
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("PATCH");

        // Cabeceras permitidas
        config.addAllowedHeader("Origin");
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("Accept");
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("X-Requested-With");
        config.addAllowedHeader("Access-Control-Request-Method");
        config.addAllowedHeader("Access-Control-Request-Headers");
        config.addAllowedHeader("Cookie");
        config.addAllowedHeader("Set-Cookie");

        config.setExposedHeaders(Arrays.asList(
                "Set-Cookie", "Cookie", "Authorization", "Content-Disposition"
        ));

        // Tiempo de cache para preflight requests
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
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
                "https://localhost/", // Cubre todos los puertos en localhost
                "http://127.0.0.1:*"  // También la IP directa
        ));

        for (String domain : companyService.getAllCompaniesDomains()) {
            config.addAllowedOrigin(domain);
        }
    }
}