package api.karbonzeromovil.Controllers.MapController;

import api.karbonzeromovil.Services.MapService.MapService;
import api.karbonzeromovil.Utils.Responses;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/apiMap")
@RestController
public class MapController {

    @Autowired
    MapService service;

    @GetMapping("/getCountryGeo/{name}")
    public JsonNode getCountryJson (@PathVariable String name) {

        JsonNode data = service.loadGeoJson(name);

        if (data == null) {
            return null;
        }
        return data;
    }

}
