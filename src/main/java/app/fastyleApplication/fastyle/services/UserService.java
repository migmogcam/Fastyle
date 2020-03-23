package app.fastyleApplication.fastyle.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import app.fastyleApplication.fastyle.model.User;
import app.fastyleApplication.fastyle.web.UserRegistrationDto;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}
