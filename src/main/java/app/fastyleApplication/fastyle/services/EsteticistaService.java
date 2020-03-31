package app.fastyleApplication.fastyle.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.fastyleApplication.fastyle.model.Cliente;
import app.fastyleApplication.fastyle.model.Esteticista;
import app.fastyleApplication.fastyle.model.Usuario;
import app.fastyleApplication.fastyle.repository.EsteticistaRepository;

@Service
public class EsteticistaService {
	
    @Autowired
    EsteticistaRepository repository;
    
    public Esteticista findByUsuario(Usuario usuario) {
    	Esteticista appUser = 
                repository.findByUsuario(usuario).orElseThrow(() -> new UsernameNotFoundException("No existe usuario"));
    	return appUser;
    }
    
    public Esteticista createOrUpdateCliente(Esteticista entity) throws Exception 
    {
    	
    	if(entity.getId()!= null) {
        Optional<Esteticista> cliente = repository.findById(entity.getId());      
        Esteticista newEntity = cliente.get();
            
            newEntity = repository.save(newEntity);
             
            return newEntity;
        } else {
            entity = repository.save(entity);
             
            return entity;
        }
    } 

     
    public List<Esteticista> getAllEsteticistas()
    {
        List<Esteticista> esteticistaList = repository.findAll();
         
        if(esteticistaList.size() > 0) {
            return esteticistaList;
        } else {
            return new ArrayList<Esteticista>();
        }
    }
     
    public Esteticista getEsteticistaById(Integer id) throws Exception 
    {
        Optional<Esteticista> esteticista = repository.findById(id);
         
        if(esteticista.isPresent()) {
            return esteticista.get();
        } else {
            throw new Exception("No esteticista record exist for given id");
        }
    }
     
     
    public void deleteEsteticistaById(Integer id) throws Exception 
    {
        Optional<Esteticista> esteticista = repository.findById(id);
         
        if(esteticista.isPresent()) 
        {
            repository.deleteById(id);
        } else {
            throw new Exception("No esteticista record exist for given id");
        }
    } 

}
