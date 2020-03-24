package app.fastyleApplication.fastyle.services;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import app.fastyleApplication.fastyle.UserDto;
import app.fastyleApplication.fastyle.model.Usuario;
import app.fastyleApplication.fastyle.repository.UsuarioRepository;

@Service
public class UsuarioService implements IUserService{
    @Autowired
    private UsuarioRepository repository;
     
    @Transactional
    @Override
    public Usuario registerNewUserAccount(UserDto accountDto){
         
        
    	
        Usuario user = new Usuario();    
        user.setName(accountDto.getName());
        user.setApellido1(accountDto.getApellido1());
        user.setApellido2(accountDto.getApellido2());
        user.setPassword(accountDto.getPassword());
        user.setEMail(accountDto.getEMail());
        user.setId(accountDto.getId());
        return repository.save(user);       
    }
//    private boolean emailExists(String email) {
//        User user = repository.findByEmail(email);
//        if (user != null) {
//            return true;
//        }
//        return false;
//    }
}