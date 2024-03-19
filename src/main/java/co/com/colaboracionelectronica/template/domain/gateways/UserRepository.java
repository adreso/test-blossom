package co.com.colaboracionelectronica.template.domain.gateways;

import co.com.colaboracionelectronica.template.domain.model.User;

public interface UserRepository {

	User findUserByIdusuario(String idusuario);
}
