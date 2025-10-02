package api.karbonzeromovil.Services.Auth;

import api.karbonzeromovil.Entities.User.UserEntity;
import api.karbonzeromovil.Services.Mail.MailService;
import api.karbonzeromovil.Services.User.UserService;
import api.karbonzeromovil.Utils.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PasswordRecoveryService {

    @Autowired
    private AuthService authService;

    @Autowired
    private MailService mailService;

    // Código temporal en memoria
    private final ConcurrentHashMap<String, CodeEntry> codes = new ConcurrentHashMap<>();

    // Clase interna para almacenar código y expiración
    private static class CodeEntry {
        String code;
        Instant expiresAt;

        CodeEntry(String code, Instant expiresAt) {
            this.code = code;
            this.expiresAt = expiresAt;
        }
    }

    // Generar y enviar código
    public boolean generateAndSendCode(String email) {
        UserEntity user = authService.getUser(email);
        if (user == null) return false;

        String code = CodeGenerator.generateCode(6); // 6 caracteres
        Instant expiresAt = Instant.now().plusSeconds(15 * 60); // 15 min
        codes.put(user.getIdUser(), new CodeEntry(code, expiresAt));

        mailService.sendRecoveryCode(email, code);
        return true;
    }

    // Verificar código
    public boolean verifyCode(String code) {
        // Buscar en todos los códigos almacenados
        for (Map.Entry<String, CodeEntry> entry : codes.entrySet()) {
            CodeEntry stored = entry.getValue();

            // Si coincide el código
            if (stored.code.equals(code)) {
                // Validar expiración
                if (Instant.now().isAfter(stored.expiresAt)) {
                    codes.remove(entry.getKey());
                    return false; // código expirado
                }

                // Si es válido, lo eliminamos para que sea de un solo uso
                codes.remove(entry.getKey());
                return true;
            }
        }
        return false; // no se encontró ese código
    }
}