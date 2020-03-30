package app.fastyleApplication.fastyle.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.fastyleApplication.fastyle.model.Cliente;
import app.fastyleApplication.fastyle.model.Usuario;
import app.fastyleApplication.fastyle.repository.ClienteRepository;

@Service
public class ClienteService{
	
    @Autowired
    ClienteRepository repository;
     
    public Cliente findByUsuario(Usuario usuario) {
    	Cliente appUser = 
                repository.findByUsuario(usuario).orElseThrow(() -> new UsernameNotFoundException("No existe usuario"));
    	return appUser;
    }

    public Cliente createOrUpdateCliente(Cliente entity) throws Exception 
    {
    	
    	if(entity.getId()!= null) {
        Optional<Cliente> cliente = repository.findById(entity.getId());      
            Cliente newEntity = cliente.get();
            
            newEntity = repository.save(newEntity);
             
            return newEntity;
        } else {
            entity = repository.save(entity);
             
            return entity;
        }
    } 
    
    public List<Cliente> getAllClientes()
    {
        List<Cliente> clienteList = repository.findAll();
         
        if(clienteList.size() > 0) {
            return clienteList;
        } else {
            return new ArrayList<Cliente>();
        }
    }
     
    public Cliente getClienteById(Integer id) throws Exception 
    {
        Optional<Cliente> cliente = repository.findById(id);
         
        if(cliente.isPresent()) {
            return cliente.get();
        } else {
            throw new Exception("No cliente record exist for given id");
        }
    }
   
     
    public void deleteClienteById(Integer id) throws Exception 
    {
        Optional<Cliente> cliente = repository.findById(id);
         
        if(cliente.isPresent()) 
        {
            repository.deleteById(id);
        } else {
            throw new Exception("No cliente record exist for given id");
        }
    }
	

}
