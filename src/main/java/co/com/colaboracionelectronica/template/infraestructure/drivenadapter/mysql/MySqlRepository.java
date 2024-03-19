package co.com.colaboracionelectronica.template.infraestructure.drivenadapter.mysql;

import org.springframework.data.repository.CrudRepository;

import co.com.colaboracionelectronica.template.infraestructure.drivenadapter.mysql.model.UserEntities;

public interface MySqlRepository extends CrudRepository<UserEntities, Integer> {

	UserEntities findUserByIdusuario(String idusuario);
}
