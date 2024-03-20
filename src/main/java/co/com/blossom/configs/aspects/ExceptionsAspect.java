package co.com.blossom.configs.aspects;

import co.com.blossom.configs.exceptions.ConstraintFacade;
import co.com.blossom.configs.exceptions.DomainException;
import co.com.blossom.configs.exceptions.InfraestructureException;
import co.com.blossom.configs.exceptions.ResourceNotFoundException;
import co.com.blossom.configs.utils.ErrorCode;
import co.com.blossom.configs.utils.StandarResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Aspect
@Slf4j
@AllArgsConstructor
public class ExceptionsAspect {

    private ConstraintFacade constraintFacade;

    @Pointcut("@annotation(co.com.blossom.configs.annotations.ExceptionsControl)")
    public void controlaExcepcion() {}

    @Around("controlaExcepcion()")
    public Object protegerLlamado(ProceedingJoinPoint proceedingJoinPoint) {
        StandarResponse response;
        HttpStatus status;

        try {
            return proceedingJoinPoint.proceed();
        } catch (ResourceNotFoundException ex) {
            log.error(ex.getLogMessage());
            response = buildErrorResponseDTO(ex.getMessage(), ex.getCode());
            status = HttpStatus.NOT_FOUND;
        } catch (InfraestructureException | DomainException ex) {
            log.error(ex.getLogMessage());
            response = buildErrorResponseDTO(ex.getMessage(), ex.getCode());
            status = HttpStatus.BAD_REQUEST;
        } catch (Throwable e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                ErrorDetail errorDetail = analyzeLogMessage(((ConstraintViolationException)e.getCause()).getSQLException().getMessage());
                log.error(errorDetail.errorCode + ": " + errorDetail.errorMessage, e);
                response = buildErrorResponseDTO(errorDetail.errorMessage, errorDetail.errorCode);
            } else {
                String outMessage= e.getMessage();
                log.error(outMessage);
                if (outMessage.contains("JDBC exception executing SQL"))
                    outMessage = "SQL ERROR: Error to execute service";
                response = buildErrorResponseDTO(outMessage, ErrorCode.GLOBAL_ERROR);
            }
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, status);
    }

    static String EXPRESSION_FK = "Cannot delete or update a parent row: a foreign key constraint fails";
    static String EXPRESSION_UK = "Duplicate entry ";
    static String EXPRESSION_FK_PARENT_NOT_EXISTS = "Cannot add or update a child row: a foreign key constraint fails";
    private ErrorDetail analyzeLogMessage(String message) {
        ErrorDetail errorDetail = new ErrorDetail();
        String effectiveMessage = null;

        errorDetail.errorCode = ErrorCode.GLOBAL_ERROR;
        errorDetail.errorMessage = "Error to execute service";

        message = identifyConstraintName(message);

        if(Objects.nonNull(constraintFacade)) {
            effectiveMessage = constraintFacade.getEffectiveMessage(message);
        }

        errorDetail.errorMessage = Objects.nonNull(effectiveMessage) && !effectiveMessage.isEmpty() ? effectiveMessage :
            message.contains(EXPRESSION_FK) ? "The selected record is linked to one or more records" :
            message.contains(EXPRESSION_FK_PARENT_NOT_EXISTS) ? "The parent record does not exist for the requested operation" :
            message.contains(EXPRESSION_UK) ? "A record exists similar to the one you are trying to create or update" :
            "Error ConstraintViolation";

        errorDetail.errorCode = ErrorCode.INFRA_ENTITY_DBCONSTRAINT_FOUND;


        return errorDetail;
    }

    private String identifyConstraintName(String message) {
        if (message.contains(EXPRESSION_FK)) {
            return message.replace(", CONSTRAINT `", ", CONSTRAINT [")
                .replace("` FOREIGN KEY ", "] FOREIGN KEY ");
        }

        if (message.contains(EXPRESSION_UK)) {
            return message.replace("' for key '", "' for key [").substring(0, message.length() - 1) + "]";
        }

        return message;
    }

    private StandarResponse buildErrorResponseDTO(String message, String codigo) {
        return StandarResponse.builder()
                .exitoso(false)
                .dato(null)
                .titulo("Error")
                .mensaje(message)
                .codigo(codigo)
                .build();
    }

    private static class ErrorDetail {
        String errorCode;
        String errorMessage;
    }
}