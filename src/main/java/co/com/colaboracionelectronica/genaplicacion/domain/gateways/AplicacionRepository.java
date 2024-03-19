package co.com.colaboracionelectronica.genaplicacion.domain.gateways;

import co.com.colaboracionelectronica.genaplicacion.domain.model.Aplicacion;


import java.util.Optional;

public interface AplicacionRepository {

    Optional<Aplicacion> findAplicacionByIdAplicacion(Integer idAplicacion);
    Optional<Aplicacion> createAplicacion(Aplicacion aplicacion);
}
