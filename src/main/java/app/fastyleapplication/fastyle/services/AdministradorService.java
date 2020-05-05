package app.fastyleapplication.fastyle.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fastyleapplication.fastyle.model.Administrador;
import app.fastyleapplication.fastyle.repository.AdministradorRepository;

@Service
public class AdministradorService {
	
    @Autowired
    AdministradorRepository repository;
     
    public List<Administrador> getAllAdministradors()
    {
        List<Administrador> administradorList = repository.findAll();
         
        if(!administradorList.isEmpty()) {
            return administradorList;
        } else {
            return new ArrayList<>();
        }
    }
     
    public Administrador getAdministradorById(Integer id) throws IllegalArgumentException 
    {
        Optional<Administrador> administrador = repository.findById(id);
         
        if(administrador.isPresent()) {
            return administrador.get();
        } else {
            throw new IllegalArgumentException("No administrador record exist for given id");
        }
    }
     
  
    public void deleteAdministradorById(Integer id) throws IllegalArgumentException 
    {
        Optional<Administrador> administrador = repository.findById(id);
         
        if(administrador.isPresent()) 
        {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("No administrador record exist for given id");
        }
    } 

}
