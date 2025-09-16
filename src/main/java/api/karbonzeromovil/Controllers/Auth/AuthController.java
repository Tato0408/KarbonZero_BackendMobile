package api.karbonzeromovil.Controllers.Auth;

import api.karbonzeromovil.Entities.User.UserEntity;
import api.karbonzeromovil.Models.DTO.User.UserDTO;
import api.karbonzeromovil.Services.Auth.AuthService;
import api.karbonzeromovil.Utils.JWTUtils;
import api.karbonzeromovil.Utils.Responses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/apiAuth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/login")
    private ResponseEntity<Map<String, Object>> login (@RequestBody UserDTO data, HttpServletResponse response) {
        System.out.println(data);
        if (data.getEmail() == null || data.getEmail().isBlank()) {
            return Responses.responseNotFound("Error, credenciales inválidas");
        }
        if (data.getPasswordUser() == null || data.getPasswordUser().isBlank()) {
            return Responses.responseNotFound("Error, credenciales inválidas");
        }

        if (service.Login(data.getEmail(), data.getPasswordUser())) {
            addTokenCookie(response, data.getEmail());
            return Responses.responseOK("Inicio de sesión exitoso");
        }
        return Responses.responseNotFound("Error, credenciales inválidas");
    }

    private void addTokenCookie (HttpServletResponse response, String email) {
        UserEntity user = service.getUser(email);

        if (user != null) {
            String token = jwtUtils.create(
                    String.valueOf(user.getIdUser()),
                    user.getEmail()
            );

            Cookie cookie = new Cookie("authToken", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(86400);
            response.addCookie(cookie);
        }
    }
}
