package co.com.blossom.configs.exceptions;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String message, String code) {
        super(code, message, null);
    }
}
