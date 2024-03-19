package co.com.colaboracionelectronica.genaplicacion.domain.services;

import co.com.colaboracionelectronica.genaplicacion.domain.gateways.AplicacionRepository;
import co.com.colaboracionelectronica.genaplicacion.domain.model.Aplicacion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AplicacionServiceImpl implements AplicacionService{

    private final AplicacionRepository repository;

    @Override
    public Aplicacion getAplicacionById(Integer idAplicacion) {
        Optional<Aplicacion> aplicacion = repository.findAplicacionByIdAplicacion(idAplicacion);
        if (aplicacion.isPresent()){
            return aplicacion.get();
        }

        return null;
    }

    @Override
    public Aplicacion createAplicacion(Aplicacion aplicacion) {
        Optional<Aplicacion> createAplicacion = repository.createAplicacion(aplicacion);
        if (createAplicacion.isPresent()){
            return createAplicacion.get();
        }
        return new Aplicacion();
    }

    @Override
    public Aplicacion editAplicacionById(Aplicacion aplicacion) {
        Optional<Aplicacion> aplicacionOptional = repository.findAplicacionByIdAplicacion(aplicacion.getIdAplicacion());
        if (aplicacionOptional.isPresent()){
            Aplicacion editAplicacion =aplicacionOptional.get();
            editAplicacion.setDescripcionAplicacion(aplicacion.getDescripcionAplicacion());
            editAplicacion.setNombreAplicacion(aplicacion.getNombreAplicacion());
            editAplicacion.setTipoAplicacion(aplicacion.getTipoAplicacion());
            editAplicacion.setBucketImagen(aplicacion.getBucketImagen());
            editAplicacion.setRutaImagen(aplicacion.getRutaImagen());
            return  repository.createAplicacion(editAplicacion).get();

        }

        return null;
    }
}
