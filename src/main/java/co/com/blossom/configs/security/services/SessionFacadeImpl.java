package co.com.blossom.configs.security.services;

import co.com.blossom.configs.security.model.UserSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SessionFacadeImpl implements SessionFacade {

    @Override
    public UserSession getUserSession() {
        return (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
