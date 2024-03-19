/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.colaboracionelectronica.external.comunes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Clase de respuesta estandar para toda petición recibida
 */
@Data
@AllArgsConstructor
@Builder
public class ResponseDTO {

    private Object dato;
    private Boolean exitoso;
    private String titulo;
    private String mensaje;
    private HttpStatus status;

}
