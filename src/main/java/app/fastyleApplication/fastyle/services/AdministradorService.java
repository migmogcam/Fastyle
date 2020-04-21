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
         
        if(administradorList.size() > 0) {
            return administradorList;
        } else {
            return new ArrayList<Administrador>();
        }
    }
     
    public Administrador getAdministradorById(Integer id) throws Exception 
    {
        Optional<Administrador> administrador = repository.findById(id);
         
        if(administrador.isPresent()) {
            return administrador.get();
        } else {
            throw new Exception("No administrador record exist for given id");
        }
    }
     
  
    public void deleteAdministradorById(Integer id) throws Exception 
    {
        Optional<Administrador> administrador = repository.findById(id);
         
        if(administrador.isPresent()) 
        {
            repository.deleteById(id);
        } else {
            throw new Exception("No administrador record exist for given id");
        }
    } 

}
