package co.com.blossom.configs.controller;


import co.com.blossom.configs.utils.ErrorCode;
import co.com.blossom.configs.utils.StandarResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class is used to customize the response of the
 * ResponseEntity when errors are found in the validation
 */

@org.springframework.web.bind.annotation.ControllerAdvice
@AllArgsConstructor
public class ControllerAdvice {

    private final MessageSource messageSource;
    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        StandarResponse responseDTO = StandarResponse.builder()
                .dato(errors)
                .mensaje(getText("common.request.validation.error"))
                .titulo(getText("common.request.validation.title"))
                .exitoso(false)
                .codigo(ErrorCode.GLOBAL_INPUTS_VALIDATION)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    private String getText(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }
}
