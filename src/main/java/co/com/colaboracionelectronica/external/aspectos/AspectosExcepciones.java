/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.colaboracionelectronica.external.aspectos;

import co.com.colaboracionelectronica.external.comunes.ResponseDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Clase de ejemplo para capturar las excepciones en los métodos anotados con @ExcepcionControl
 */
@Component
@Aspect
public class AspectosExcepciones {

    @Pointcut("@annotation(co.com.colaboracionelectronica.external.anotaciones.ExcepcionControl)")
    public void controlaExcepcion() {}

    @Around("controlaExcepcion()")
    public Object protegerLlamado(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            ResponseDTO responseDTO = buildErrorResponseDTO("Ocurrió un error: " + e.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseDTO buildErrorResponseDTO(String message) {
        return ResponseDTO.builder()
                .exitoso(true)
                .dato(null)
                .titulo("Error")
                .mensaje(message)
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
