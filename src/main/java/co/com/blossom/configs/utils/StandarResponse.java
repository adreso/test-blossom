package co.com.blossom.configs.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Standard response object
 */
@Data
@AllArgsConstructor
@Builder
public class StandarResponse<T> {

    private T dato;
    private Boolean exitoso;
    private String titulo;
    private String mensaje;
    private String codigo;
}
