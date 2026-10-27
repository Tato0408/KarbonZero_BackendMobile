package api.karbonzeromovil.Services.MapService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class MapService {
    public JsonNode loadGeoJson(String country) {
       try {
           ObjectMapper mapper = new ObjectMapper();
           ClassPathResource resource = new ClassPathResource("GeoJson/" + country + ".geojson");
           try (InputStream is = resource.getInputStream()) {
               return mapper.readTree(is);
           }
       } catch (Exception e) {
           return null;
       }
    }
}
