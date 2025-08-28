package api.karbonzeromovil.Exceptions;

public class ExceptionNotFound extends RuntimeException {

    public ExceptionNotFound() {
        super("Data Not found");
    }
    public ExceptionNotFound(String message) {super(message);}
}



