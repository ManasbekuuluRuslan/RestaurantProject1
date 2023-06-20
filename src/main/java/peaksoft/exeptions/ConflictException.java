package peaksoft.exeptions;

public class ConflictException extends RuntimeException{

    public ConflictException(String message) {
        super(message);
    }
}
