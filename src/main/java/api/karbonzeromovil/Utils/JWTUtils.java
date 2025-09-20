package api.karbonzeromovil.Utils;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.swing.text.html.parser.Parser;
import java.util.Date;

@Component
public class JWTUtils {

    private final Dotenv dotenv = Dotenv.load();
    private final Logger log = LoggerFactory.getLogger(JWTUtils.class);

    /**
     * Metodo para crea JWT
     * @param id
     * @param email
     * @return
     */
    public String create(String id, String email, String rol){
        //Decodifica el secreto Base64 y crea una clave HMAC-SHA segura
        SecretKey signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(dotenv.get("SECRET_KEY_JWT")));
        long expirationMs = Long.parseLong(dotenv.get("JWT_EXPIRATION"));
        //Obtiene la fecha catual y calcula la fecha de expiración
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);

        //Construye el token con sus componentes
        return Jwts.builder()
                .setId(id)                                              // ID único (JWT ID)
                .setIssuedAt(now)                                       // Fecha de emisión
                .setSubject(email)                                     // Sujeto (usuario)
                .claim("id", id)
                .claim("rol", rol)
                .setIssuer(dotenv.get("SECURITY_JWT_ISSUER"))                                      // Emisor del token
                .setExpiration(expirationMs >= 0 ? expiration : null)   // Expiración (si es >= 0)
                .signWith(signingKey, SignatureAlgorithm.HS256)         // Firma con algoritmo HS256
                .compact();                                             // Convierte a String compacto
    }

    public String extractRol(String token){
        Claims claims = parseToken(token);
        return claims.get("rol",String.class);
    }

    /**
     * Obtiene el subject (nombre) del JWT
     * @param jwt Token JWT como String
     * @return String con el subject del token
     */
    public String getValue(String jwt){
        //Parsea los claims y devuelve el subject
        Claims claims = parseClaims(jwt);
        return claims.getSubject();
    }

    /**
     * Obtine el ID del JWT
     * @param jwt
     * @return
     */
    public String getKey(String jwt){
        // Parsea los claims y devuelve el ID
        Claims claims = parseClaims(jwt);
        return claims.getId();
    }

    /**
     * Parsea y valida el token
     * @param jwt
     * @return
     * @throws ExpiredJwtException
     * @throws MalformedJwtException
     */
    public Claims parseToken(String jwt) throws ExpiredJwtException, MalformedJwtException {
        return parseClaims(jwt);
    }

    /**
     * Validación del token
     * @param token
     * @return
     */
    public boolean validate(String token){
        try{
            parseClaims(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            log.warn("Token inválido: {}", e.getMessage());
            return false;
        }
    }

    //######################## METODOS COMPLEMENTARIOS ########################

    /**
     * Metodo privado para parsear los claims de un JWT
     * @param jwt Token a parsear
     * @return Claims del token
     */
    private Claims parseClaims(String jwt) {
        //Configura el parse con la clave de firma y parsea el token
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(dotenv.get("SECRET_KEY_JWT"))))  // Clave de firma
                .build()
                .parseClaimsJws(jwt)
                .getBody();

    }
}
