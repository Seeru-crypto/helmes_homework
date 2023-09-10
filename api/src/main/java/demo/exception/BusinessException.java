package demo.exception;

public abstract class BusinessException extends RuntimeException {
    public static final String USER_NAME_EXISTS = "User name already exists";
    protected BusinessException(String message) {
        super(message);
    }
}
