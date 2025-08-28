package api.karbonzeromovil.Exceptions;

public class UpdateException extends RuntimeException {
    public UpdateException (String message, Throwable cause){super(message, cause);}
    public UpdateException(String message) {
        super(message);
    }
}
