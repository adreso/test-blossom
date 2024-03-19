//package co.com.colaboracionelectronica.configs;
//
//import co.com.colaboracionelectronica.external.comunes.ResponseDTO;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Clase para personalizar la respuesta del ResponseEntity al encontrar errores en la validación
// * de atributos
// */
//
//@ControllerAdvice
//public class ManejadorExcepcionResponseEntity extends ResponseEntityExceptionHandler {
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) ->{
//
//            String fieldName = ((FieldError) error).getField();
//            String message = error.getDefaultMessage();
//            errors.put(fieldName, message);
//        });
//
//        ResponseDTO responseDTO = ResponseDTO.builder()
//                .dato(errors)
//                .mensaje("Errores")
//                .titulo("Error")
//                .exitoso(false)
//                .status(HttpStatus.BAD_REQUEST)
//                .build();
//
//        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
//    }
//}
