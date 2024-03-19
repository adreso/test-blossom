package co.com.colaboracionelectronica.genaplicacion.domain.services;

import co.com.colaboracionelectronica.genaplicacion.domain.model.Aplicacion;

public interface AplicacionService {

    Aplicacion getAplicacionById(Integer idAplicacion);
    Aplicacion createAplicacion(Aplicacion aplicacion);
    Aplicacion editAplicacionById(Aplicacion aplicacion);


}
