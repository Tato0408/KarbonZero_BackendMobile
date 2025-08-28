package api.karbonzeromovil.Exceptions;

import org.hibernate.sql.Delete;

public class DeleteException extends RuntimeException {
    public DeleteException(String message, Throwable cause){super(message, cause);}
    public DeleteException(String message) {
        super(message);
    }
}
