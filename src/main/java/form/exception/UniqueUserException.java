package form.exception;

@SuppressWarnings("serial")
public class UniqueUserException extends RuntimeException {
    public UniqueUserException(String s) {
        super(s);
    }
    public UniqueUserException(String s , Throwable t){
        super(s,t);
    }
    public UniqueUserException(Throwable t){
        super(t);
    }
}
