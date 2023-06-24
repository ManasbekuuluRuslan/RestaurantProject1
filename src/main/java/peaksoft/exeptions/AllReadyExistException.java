package peaksoft.exeptions;

public class AllReadyExistException extends RuntimeException{
    public AllReadyExistException() {
    }
    public AllReadyExistException(String message) {
        super(message);
    }
}
