package co.com.blossom.configs.exceptions;

public class InfraestructureException extends BaseException {
    public InfraestructureException(String message, String code) {
        super(code, message, null);
    }
    public InfraestructureException(String message, String code, String exceptionMsg) {
        super(code, message, exceptionMsg);
    }
}
