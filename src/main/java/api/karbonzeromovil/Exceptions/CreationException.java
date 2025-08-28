package api.karbonzeromovil.Exceptions;

public class CreationException extends RuntimeException {
    public CreationException(String message, Throwable cause){super(message, cause);}
    public CreationException(String message) {
        super(message);
    }
}
