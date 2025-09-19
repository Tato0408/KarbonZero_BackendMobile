package api.karbonzeromovil.Config;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Argon2PasswordEncoder {
    private static final int ITERATIONS = 10;
    private static final int MEMORY = 32768; //
    private static final int PARALLELISM = 1;

    // Crear instancia de Argon2id
    private Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    public String HashPassword(String rawPassword) {
        // Configuración de parámetros (ajusta según necesidades)
        // Generar hash (incluye salt automáticamente en el string resultante)
        return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, rawPassword);
    }

    public boolean decodePassword(String PasswordBD, String Password){
        return argon2.verify(PasswordBD,Password);
    }
}
