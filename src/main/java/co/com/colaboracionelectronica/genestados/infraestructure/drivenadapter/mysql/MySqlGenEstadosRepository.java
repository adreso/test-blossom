package co.com.colaboracionelectronica.genestados.infraestructure.drivenadapter.mysql;

import co.com.colaboracionelectronica.genestados.domain.model.projections.GenEstadosFullDescripcion;
import co.com.colaboracionelectronica.genestados.domain.model.projections.GenEstadosProjection;
import co.com.colaboracionelectronica.genestados.infraestructure.drivenadapter.mysql.model.GenEstadosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MySqlGenEstadosRepository extends JpaRepository<GenEstadosEntity, Integer> {
    
    Optional<GenEstadosEntity> findByEstado(Integer estado);

    @Query("select e from GenEstadosEntity e where e.estado=?1")
    Optional<GenEstadosProjection> findByEstadoProjection(Integer estado);
    
    @Query(value = "SELECT en.nombreestado as nombreEstado , en.descripcion as descripcion ,  un.idusuario as usuario FROM GenEstadosEntity en JOIN  UserEntities un ON un.id = en.idusuarioalta WHERE en.estado =?1" )
    GenEstadosFullDescripcion findEstadoDescripcionByEstado(Integer estado);

}
