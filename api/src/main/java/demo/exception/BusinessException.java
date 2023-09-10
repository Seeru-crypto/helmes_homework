package demo.exception;

public abstract class BusinessException extends RuntimeException {
    public static final String USER_NAME_EXISTS = "User name already exists";
    public static final String USER_TOS = "User must agree to terms of service";
    protected BusinessException(String message) {
        super(message);
    }
}
