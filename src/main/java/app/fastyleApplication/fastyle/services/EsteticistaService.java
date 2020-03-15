package app.fastyleApplication.fastyle.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fastyleApplication.fastyle.model.Esteticista;
import app.fastyleApplication.fastyle.repository.EsteticistaRepository;

@Service
public class EsteticistaService {
	
    @Autowired
    EsteticistaRepository repository;
     
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
     
    public Esteticista createOrUpdateEsteticista(Esteticista entity) throws Exception 
    {
        Optional<Esteticista> esteticista = repository.findById(entity.getId());
         
        if(esteticista.isPresent()) 
        {
            Esteticista newEntity = esteticista.get();
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
