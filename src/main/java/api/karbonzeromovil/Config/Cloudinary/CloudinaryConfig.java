package api.karbonzeromovil.Config.Cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Value("${CLOUDINARY_CLOUD_NAME:}")
    private String cloudName;
    @Value("${CLOUDINARY_API_KEY:}")
    private String apiKey;
    @Value("${CLOUDINARY_API_SECRET:}")
    private String apiSecret;

    /**
     * Agregar las dos dependencias al pom.xml
     *          <dependency>
     * 			<groupId>com.cloudinary</groupId>
     * 			<artifactId>cloudinary-http5</artifactId>
     * 			<version>2.0.0</version>
     * 		</dependency>
     *
     * 		<dependency>
     * 			<groupId>com.cloudinary</groupId>
     * 			<artifactId>cloudinary-taglib</artifactId>
     * 			<version>2.0.0</version>
     * 		</dependency>
     * @return
     */

    @Bean
    public Cloudinary cloudinary(){
        //Crear un Map para almacenar la configuración necesaria para Cloudinary
        Map<String,  String> config = new HashMap<>();

        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);

        return new Cloudinary(config);
    }
}
