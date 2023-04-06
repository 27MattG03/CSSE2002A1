package srg.exceptions;

public class InsufficientResourcesException extends Exception{
    public InsufficientResourcesException() {
        super();
    }
    public InsufficientResourcesException(String message){
        super(message);
    }
}
