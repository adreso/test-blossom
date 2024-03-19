package co.com.colaboracionelectronica.genestados.domain.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de GenEstados
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenEstadosDTO {

    @NotNull(message = "El campo no puede ser nulo")
    private Integer estado;

    private Integer tipoestado;

    @NotNull(message = "El campo no puede ser nulo")
    @Size(min = 3, message = "Se requiere mínimo {min} caracteres")
    private String nombreestado;

    @NotNull(message = "El campo no puede ser nulo")
    private String descripcion;

    @NotNull(message = "El campo no puede ser nulo")
    @Min(value = 0, message = "El valor mínimo es {value}")
    @Max(value = 1, message = "El valor máximo es {value}")
    private Integer activo;

    @NotNull(message = "El campo no puede ser nulo")
    private Integer idusuarioalta;
}
