package api.karbonzeromovil.Controllers.Auth;

import api.karbonzeromovil.Entities.User.UserEntity;
import api.karbonzeromovil.Models.DTO.User.UserDTO;
import api.karbonzeromovil.Models.DTO.recoveryPassword.ForgotPasswordDTO;
import api.karbonzeromovil.Services.Auth.AuthService;
import api.karbonzeromovil.Services.Auth.PasswordRecoveryService;
import api.karbonzeromovil.Services.User.UserService;
import api.karbonzeromovil.Utils.JWTUtils;
import api.karbonzeromovil.Utils.Responses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.JSqlParserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/apiAuth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordRecoveryService passwordRecoveryService;

    @PostMapping("/login")
    private ResponseEntity<Map<String, Object>> login (@RequestBody UserDTO data, HttpServletResponse response) {
        if (data.getEmail() == null || data.getEmail().isBlank()) {
            return Responses.responseNotAuthenticated("Error, credenciales inválidas");
        }
        if (data.getPasswordUser() == null || data.getPasswordUser().isBlank()) {
            return Responses.responseNotAuthenticated("Error, credenciales inválidas");
        }

        if (service.Login(data.getEmail(), data.getPasswordUser())) {
            addTokenCookie(response, data.getEmail());
            return Responses.responseOK("Inicio de sesión exitoso");
        }
        return Responses.responseNotAuthenticated("Error, credenciales inválidas");
    }

    private void addTokenCookie (HttpServletResponse response, String email) {
        UserEntity user = service.getUser(email);

        if (user != null) {
            String token = jwtUtils.create(
                    String.valueOf(user.getIdUser()),
                    user.getEmail(),
                    String.valueOf(user.getStatus())
            );

            Cookie cookie = new Cookie("authToken", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(86400);
            response.addCookie(cookie);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return Responses.responseNotAuthenticated("No autenticado");
            }

            // Manejar diferentes tipos de Principal
            String email;
            if (authentication.getPrincipal() instanceof UserDetails userDetails) {
                email = userDetails.getUsername();
            } else {
                email = authentication.getName();
            }

            UserEntity user = service.getUser(email);

            if (user == null) {
                return Responses.responseNotAuthenticated("No autenticado");
            }

            return ResponseEntity.ok(new UserService().UsersToDTO(user));

        } catch (Exception e) {
            //log.error("Error en /me endpoint: ", e);
            return Responses.responseServerError(e, "Error obteniendo datos de usuario");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletRequest request, HttpServletResponse response) {
        // Crear cookie de expiración con SameSite=None
        String cookieValue = "authToken=; Path=/; HttpOnly; Secure; SameSite=None; MaxAge=0; Domain=localhost";

        response.addHeader("Set-Cookie", cookieValue);
        //response.addHeader("Access-Control-Allow-Credentials", "true"); <-- ESTO NO DEBEN AGREGARLO
        response.addHeader("Access-Control-Expose-Headers", "Set-Cookie");

        // También agregar headers CORS para la respuesta
        String origin = request.getHeader("Origin");
        if (origin != null &&
                (origin.contains("localhost") || origin.contains("herokuapp.com"))) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        return Responses.responseOK("logout exitoso");
    }
    //ENDPOINTS DE RECUPERACION DE CONTRASEÑA
    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDTO dto) {
        boolean sent = passwordRecoveryService.generateAndSendCode(dto.getEmail());
        if (!sent) {
            return ResponseEntity.badRequest().body(Map.of("message", "Correo no registrado"));
        }
        return ResponseEntity.ok(Map.of("message", "Código enviado"));
    }


    @PostMapping("/verifyCode")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> body) {
        String code = body.get("code");

        boolean valid = passwordRecoveryService.verifyCode(code);

        if (!valid) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Código inválido o expirado"));
        }

        return ResponseEntity.ok(Map.of("message", "Código válido"));
    }
}
