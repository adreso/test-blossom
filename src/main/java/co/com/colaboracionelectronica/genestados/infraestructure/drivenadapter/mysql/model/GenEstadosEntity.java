package co.com.colaboracionelectronica.genestados.infraestructure.drivenadapter.mysql.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "GenEstados")
@Data
public class GenEstadosEntity {

    @Id
    private Integer estado;
    private Integer tipoestado;
    private String nombreestado;
    private String descripcion;
    private Integer activo;
    private Integer idusuarioalta;       
    private Date fechaalta;
    private Integer idusuariomod;
    private Date fechamod;
    private Integer idusuariobaja;
    private Date fechabaja;

    @PrePersist
    public void valoresPorDefecto(){
        this.fechaalta = new Date();
    }
}
