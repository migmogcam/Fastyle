package app.fastyleApplication.fastyle.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.fastyleApplication.fastyle.model.Authority;
import app.fastyleApplication.fastyle.model.Usuario;
import app.fastyleApplication.fastyle.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {
	
    @Autowired
    UsuarioRepository repository;
     
     
    public Usuario createOrUpdateCliente(Usuario entity) throws Exception {
    	
    	if(entity.getId()!= null) {
	        Optional<Usuario> cliente = repository.findById(entity.getId());
	        Usuario newEntity = new Usuario();
	        if(cliente.isPresent()) {
	            newEntity = cliente.get();
	            newEntity.setApellido1(entity.getApellido1());
	            newEntity.setApellido2(entity.getApellido2());
	            newEntity.setName(entity.getName());
	            newEntity.setCiudad(entity.getCiudad());
	            newEntity.setProvincia(entity.getProvincia());
	            newEntity.setEMail(entity.getEMail());
	            newEntity.setDireccion(entity.getDireccion());
	            newEntity.setEdad(entity.getEdad());
	        }
            newEntity = repository.save(newEntity);
             
            return newEntity;
        } else {
            entity = repository.save(entity);
             
            return entity;
        }
    } 
     
    
    public Usuario findByUsuario(String usuario) {
    	return repository.findByUsuario(usuario).orElseThrow(() -> new UsernameNotFoundException("No existe usuario"));
 
    }
    
    public void deleteUsuarioById(Integer id) throws Exception {
		Optional<Usuario> usuario = repository.findById(id);

		if (usuario.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new Exception("No cliente record exist for given id");
		}
	}
    

	@Override
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
		
	     //Buscar el usuario con el repositorio y si no existe lanzar una exepcion
	     Usuario appUser = 
	                  repository.findByUsuario(usuario).orElseThrow(() -> new UsernameNotFoundException("No existe usuario"));

	    //Mapear nuestra lista de Authority con la de spring security 
	     List<GrantedAuthority> grantList = new ArrayList<>();
	     for (Authority authority: appUser.getAuthorities()) {
	         // ROLE_USER, ROLE_ADMIN,..
	         GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
	             grantList.add(grantedAuthority);
	     }

	    //Crear El objeto UserDetails que va a ir en sesion y retornarlo.
	    return (UserDetails) new User(appUser.getUsuario(), appUser.getPassword(), grantList);
	    }
	

}
