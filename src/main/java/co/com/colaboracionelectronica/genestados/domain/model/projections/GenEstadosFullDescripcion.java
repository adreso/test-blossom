/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.com.colaboracionelectronica.genestados.domain.model.projections;

import org.springframework.beans.factory.annotation.Value;

/**
 * Ejemplo de proyección sencillo
 */
public interface GenEstadosFullDescripcion {
 
    @Value(value = "#{target.nombreEstado + ' ' + target.descripcion}")
    public String getFullDescripcion();
    public String getUsuario();
    
}
