package co.com.colaboracionelectronica.genaplicacion.domain.model;

import lombok.*;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Aplicacion {

    private Integer idAplicacion;
    private String nombreAplicacion;
    private String descripcionAplicacion;
    private String bucketImagen;
    private String rutaImagen;
    private String tipoAplicacion;


}
