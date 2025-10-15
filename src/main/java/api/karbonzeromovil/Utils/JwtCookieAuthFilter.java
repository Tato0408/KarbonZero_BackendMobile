package api.karbonzeromovil.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtCookieAuthFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtCookieAuthFilter.class);
    private static final String AUTH_COOKIE_NAME = "authToken";
    private static final String CHANNEL_COOKIE_NAME = "channelToken";
    private final JWTUtils jwtUtils;

    @Autowired
    public JwtCookieAuthFilter(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Si es endpoint público, continuar sin validación
        if (isPublicEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extraer AMBOS tokens
            Map<String, String> tokens = extractAllTokensFromCookies(request);

            if (tokens.isEmpty()) {
                sendError(response, "Token no encontrado", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // Colección para almacenar todos los roles
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            String username = null;

            // Procesar authToken
            if (tokens.containsKey(AUTH_COOKIE_NAME)) {
                try {
                    String authToken = tokens.get(AUTH_COOKIE_NAME);
                    Claims claims = jwtUtils.parseToken(authToken);
                    username = claims.getSubject();
                    String rol = jwtUtils.extractRol(authToken);
                    authorities.add(new SimpleGrantedAuthority("ROL_" + rol));
                } catch (Exception e) {
                    log.warn("Error procesando authToken: {}", e.getMessage());
                }
            }

            // Procesar channelToken
            if (tokens.containsKey(CHANNEL_COOKIE_NAME)) {
                try {
                    String channelToken = tokens.get(CHANNEL_COOKIE_NAME);
                    Claims claims = jwtUtils.parseToken(channelToken);
                    if (username == null) {
                        username = claims.getSubject();
                    }
                    String rol = jwtUtils.extractRol(channelToken);
                    authorities.add(new SimpleGrantedAuthority("ROL_" + rol));
                } catch (Exception e) {
                    log.warn("Error procesando channelToken: {}", e.getMessage());
                }
            }

            // Verificar que al menos un token sea válido
            if (authorities.isEmpty() || username == null) {
                sendError(response, "Tokens inválidos", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // Crear autenticación con TODOS los roles combinados
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );

            // Log para debug
            log.debug("Usuario autenticado: {} con roles: {}",
                    username,
                    authorities.stream()
                            .map(a -> a.getAuthority())
                            .collect(Collectors.joining(", "))
            );

            // Establecer autenticación en contexto
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Continuar con la cadena de filtros
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            log.warn("Token expirado: {}", e.getMessage());
            sendError(response, "Token expirado", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (MalformedJwtException e) {
            log.warn("Token malformado: {}", e.getMessage());
            sendError(response, "Token inválido", HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            log.error("Error de autenticación", e);
            sendError(response, "Error de autenticación", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // Extraer TODAS las cookies de autenticación
    private Map<String, String> extractAllTokensFromCookies(HttpServletRequest request) {
        Map<String, String> tokens = new HashMap<>();
        Cookie[] cookies = request.getCookies();

        if (cookies == null) return tokens;

        for (Cookie cookie : cookies) {
            if (AUTH_COOKIE_NAME.equals(cookie.getName())) {
                tokens.put(AUTH_COOKIE_NAME, cookie.getValue());
            } else if (CHANNEL_COOKIE_NAME.equals(cookie.getName())) {
                tokens.put(CHANNEL_COOKIE_NAME, cookie.getValue());
            }
        }

        return tokens;
    }

    private void sendError(HttpServletResponse response, String message, int status) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        response.getWriter().write(String.format(
                "{\"error\": \"%s\", \"status\": %d}", message, status));
    }

    private boolean isPublicEndpoint(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        return (path.equals("/apiAuth/login") && "POST".equals(method) ||
                path.equals("/apiAuth/logout") && "POST".equals(method) ||
                path.equals("/apiAuth/forgotPassword") && "POST".equals(method) ||
                path.equals("/apiAuth/verifyCode") && "POST".equals(method) ||
                path.equals("/apiUser/insertUser") && "POST".equals(method) ||
                path.equals("/apiAuth/authenticateCompany") && "POST".equals(method) ||
                path.startsWith("/apiUser/updateUserPassword/") && "PUT".equals(method))
        ;
    }
}