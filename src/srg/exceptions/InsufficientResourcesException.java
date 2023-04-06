package srg.exceptions;

public class InsufficientResourcesException extends Exception{
    /**
     *
     */
    public InsufficientResourcesException() {
        super();
    }

    /**
     *
     * @param message
     */
    public InsufficientResourcesException(String message){
        super(message);
    }
}
