package peaksoft.exeptions;
public class BadCredentialException extends RuntimeException{
    public BadCredentialException(String message) {
        super(message);
    }
}