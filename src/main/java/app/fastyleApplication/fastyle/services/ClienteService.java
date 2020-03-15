package app.fastyleApplication.fastyle.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fastyleApplication.fastyle.model.Cliente;
import app.fastyleApplication.fastyle.repository.ClienteRepository;

@Service
public class ClienteService {
	
    @Autowired
    ClienteRepository repository;
     
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
     
    public Cliente createOrUpdateCliente(Cliente entity) throws Exception 
    {
        Optional<Cliente> cliente = repository.findById(entity.getId());
         
        if(cliente.isPresent()) 
        {
            Cliente newEntity = cliente.get();
            newEntity.setApellido1(entity.getApellido1());
            newEntity.setApellido2(entity.getApellido2());
            newEntity.setName(entity.getName());
            newEntity.setCiudad(entity.getCiudad());
            newEntity.setProvincia(entity.getProvincia());
            newEntity.setEMail(entity.getEMail());
            
            newEntity = repository.save(newEntity);
             
            return newEntity;
        } else {
            entity = repository.save(entity);
             
            return entity;
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
