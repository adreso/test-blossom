package co.com.colaboracionelectronica.template.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.colaboracionelectronica.template.domain.gateways.UserRepository;
import co.com.colaboracionelectronica.template.domain.model.User;

@Service
public class UserServicesImpl implements UserServices{

	@Autowired
	private UserRepository drivenAdapter;

	@Override
	public User GetUserByUserName(String userName) {
		
		return drivenAdapter.findUserByIdusuario(userName);
	}
}
