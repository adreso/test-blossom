package co.com.colaboracionelectronica.genaplicacion.infraestructure.drivenadapter.mysql;

import co.com.colaboracionelectronica.genaplicacion.infraestructure.drivenadapter.mysql.model.GenAplicacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AplicacionJPARepository extends JpaRepository<GenAplicacionEntity, Integer> {

    GenAplicacionEntity findAplicacionByIdAplicacion(Integer idAplicacion);
}
