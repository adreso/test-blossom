package co.com.colaboracionelectronica.template.application.services;

import co.com.colaboracionelectronica.template.domain.model.User;

public interface UserServices {

	User GetUserByUserName(String userName);
}
