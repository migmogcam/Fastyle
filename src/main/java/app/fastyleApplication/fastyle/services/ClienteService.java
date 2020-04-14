package app.fastyleApplication.fastyle.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.fastyleApplication.fastyle.model.Cita;
import app.fastyleApplication.fastyle.model.Cliente;
import app.fastyleApplication.fastyle.model.Usuario;
import app.fastyleApplication.fastyle.repository.CitaRepository;
import app.fastyleApplication.fastyle.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repository;
	
	@Autowired
	CitaRepository citaRepo;

	public Cliente findByUsuario(Usuario usuario) {
		Cliente appUser = repository.findByUsuario(usuario)
				.orElseThrow(() -> new UsernameNotFoundException("No existe usuario"));
		return appUser;
	}

	public Cliente createOrUpdateCliente(Cliente entity) throws Exception {

		if (entity.getId() != null) {
			Optional<Cliente> cliente = repository.findById(entity.getId());
			Cliente newEntity = cliente.get();

			newEntity.getUsuario().setApellido1(entity.getUsuario().getApellido1());
			newEntity.getUsuario().setApellido2(entity.getUsuario().getApellido2());
			newEntity.getUsuario().setCiudad(entity.getUsuario().getCiudad());
			newEntity.getUsuario().setEMail(entity.getUsuario().getEMail());
			newEntity.getUsuario().setName(entity.getUsuario().getName());
			newEntity.getUsuario().setProvincia(entity.getUsuario().getProvincia());
			newEntity.getUsuario().setUsuario(entity.getUsuario().getUsuario());
			newEntity.getUsuario().setDireccion(entity.getUsuario().getDireccion());
			newEntity.getUsuario().setEdad(entity.getUsuario().getEdad());
			
			if(null != entity.getUsuario().getPassword() && !"".equals(entity.getUsuario().getPassword())) {
				newEntity.getUsuario().setPassword(entity.getUsuario().getPassword());
			}
			
			newEntity = repository.save(newEntity);

			return newEntity;
		} else {
			entity = repository.save(entity);

			return entity;
		}
	}

	public List<Cliente> getAllClientes() {
		List<Cliente> clienteList = repository.findAll();

		if (clienteList.size() > 0) {
			return clienteList;
		} else {
			return new ArrayList<Cliente>();
		}
	}

	public Cliente getClienteById(Integer id) throws Exception {
		Optional<Cliente> cliente = repository.findById(id);

		if (cliente.isPresent()) {
			return cliente.get();
		} else {
			throw new Exception("No cliente record exist for given id");
		}
	}

	public void deleteClienteById(Integer id) throws Exception {
		Optional<Cliente> cliente = repository.findById(id);

		if (cliente.isPresent()) {
			if(!cliente.get().getCitas().isEmpty()) {
			for(Cita c : cliente.get().getCitas()) {
				c.setCliente(null);
				citaRepo.saveAndFlush(c);
			}
			}
			repository.deleteById(id);
		} else {
			throw new Exception("No cliente record exist for given id");
		}
	}

}
