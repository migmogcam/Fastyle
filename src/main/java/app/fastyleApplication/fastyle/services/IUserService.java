package app.fastyleApplication.fastyle.services;

import app.fastyleApplication.fastyle.UserDto;
import app.fastyleApplication.fastyle.model.Usuario;

public interface IUserService {
    Usuario registerNewUserAccount(UserDto accountDto);    
  
}