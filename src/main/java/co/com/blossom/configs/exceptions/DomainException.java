package co.com.blossom.configs.exceptions;

public class DomainException extends BaseException {
    public DomainException(String message, String code) {
        super(code, message, null);
    }
}
