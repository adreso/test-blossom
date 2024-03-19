package co.com.colaboracionelectronica.genaplicacion.infraestructure.drivenadapter.mysql.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "GenAplicaciones")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenAplicacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idAplicacion;

    @Column(name = "nombreaplicacion")
    private String nombreAplicacion;

    @Column(name = "descripcionaplicacion")
    private String descripcionAplicacion;

    @Column(name = "bucketimagen")
    private String bucketImagen;

    @Column(name = "rutaimagen")
    private String rutaImagen;

    @Column(name = "tipoaplicacion")
    private String tipoAplicacion;


}
