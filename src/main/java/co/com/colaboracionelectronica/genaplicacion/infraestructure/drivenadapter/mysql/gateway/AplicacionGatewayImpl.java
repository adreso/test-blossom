package co.com.colaboracionelectronica.genaplicacion.infraestructure.drivenadapter.mysql.gateway;

import co.com.colaboracionelectronica.genaplicacion.domain.gateways.AplicacionRepository;
import co.com.colaboracionelectronica.genaplicacion.domain.model.Aplicacion;
import co.com.colaboracionelectronica.genaplicacion.infraestructure.drivenadapter.mysql.mapper.AplicacionMapper;
import co.com.colaboracionelectronica.genaplicacion.infraestructure.drivenadapter.mysql.model.GenAplicacionEntity;
import co.com.colaboracionelectronica.genaplicacion.infraestructure.drivenadapter.mysql.AplicacionJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AplicacionGatewayImpl implements AplicacionRepository {
    private final AplicacionJPARepository repository;

    private final AplicacionMapper aplicacionMapper;
    @Override

    public Optional<Aplicacion> findAplicacionByIdAplicacion(Integer idAplicacion) {
        GenAplicacionEntity genAplicacionEntity =repository.findAplicacionByIdAplicacion(idAplicacion);
        return Optional.ofNullable(aplicacionMapper.convertToEntitiesToModel(genAplicacionEntity));
    }
    public Optional<Aplicacion> createAplicacion(Aplicacion aplicacion) {
        GenAplicacionEntity genAplicacion =aplicacionMapper.convertToModelToEntities(aplicacion);
        GenAplicacionEntity genAplicacionEntity =repository.save(genAplicacion);
        return Optional.ofNullable(aplicacionMapper.convertToEntitiesToModel(genAplicacionEntity));
    }
}
