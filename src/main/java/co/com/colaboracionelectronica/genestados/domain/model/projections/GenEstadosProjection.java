package co.com.colaboracionelectronica.genestados.domain.model.projections;

/**
 * Ejemplo de la misma clase DTO de GenEstados pero como una interfaz de proyección
 */
public interface GenEstadosProjection {
    public Integer getEstado();
    public Integer getTipoestado();
    public String getNombreestado();
    public String getDescripcion();
    public Integer getActivo();
    public Integer getIdusuarioalta();
}
