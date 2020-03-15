package app.fastyleApplication.fastyle.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fastyleApplication.fastyle.model.Administrador;
import app.fastyleApplication.fastyle.repository.AdministradorRepository;

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
     
    public Administrador createOrUpdateAdministrador(Administrador entity) throws Exception 
    {
        Optional<Administrador> administrador = repository.findById(entity.getId());
         
        if(administrador.isPresent()) 
        {
            Administrador newEntity = administrador.get();
            newEntity.setApellido1(entity.getApellido1());
            newEntity.setApellido2(entity.getApellido2());
            newEntity.setName(entity.getName());
            newEntity.setCiudad(entity.getCiudad());
            newEntity.setProvincia(entity.getProvincia());
            
            newEntity = repository.save(newEntity);
             
            return newEntity;
        } else {
            entity = repository.save(entity);
             
            return entity;
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
