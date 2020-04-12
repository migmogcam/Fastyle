package app.fastyleApplication.fastyle.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.fastyleApplication.fastyle.model.Cita;
import app.fastyleApplication.fastyle.model.Esteticista;
import app.fastyleApplication.fastyle.model.ServicioEstetico;
import app.fastyleApplication.fastyle.model.Usuario;
import app.fastyleApplication.fastyle.repository.CitaRepository;
import app.fastyleApplication.fastyle.repository.EsteticistaRepository;

@Service
public class EsteticistaService {

	@Autowired
	EsteticistaRepository repository;

	@Autowired
	CitaRepository citaRepo;
	
	@Autowired
	ServicioEsteticoService servicioEstetico;
	
	public Esteticista findByUsuario(Usuario usuario) {
		Esteticista appUser = repository.findByUsuario(usuario)
				.orElseThrow(() -> new UsernameNotFoundException("No existe usuario"));
		return appUser;
	}

	public Esteticista createOrUpdateCliente(Esteticista entity) throws Exception {

		if (entity.getId() != null) {
			Optional<Esteticista> cliente = repository.findById(entity.getId());
			Esteticista newEntity = cliente.get();

			newEntity.getUsuario().setApellido1(entity.getUsuario().getApellido1());
			newEntity.getUsuario().setApellido2(entity.getUsuario().getApellido2());
			newEntity.getUsuario().setCiudad(entity.getUsuario().getCiudad());
			newEntity.getUsuario().setEMail(entity.getUsuario().getEMail());
			newEntity.getUsuario().setName(entity.getUsuario().getName());
			newEntity.getUsuario().setProvincia(entity.getUsuario().getProvincia());
			newEntity.getUsuario().setDireccion(entity.getUsuario().getDireccion());
			newEntity.getUsuario().setUsuario(entity.getUsuario().getUsuario());
			newEntity.setDescripcion(entity.getDescripcion());
			newEntity.setImagenes(entity.getImagenes());

			if (null != entity.getUsuario().getPassword() && !"".equals(entity.getUsuario().getPassword())) {
				newEntity.getUsuario().setPassword(entity.getUsuario().getPassword());
			}

			newEntity = repository.save(newEntity);

			return newEntity;
		} else {
			entity = repository.save(entity);

			return entity;
		}
	}

	public List<Esteticista> getAllEsteticistas() {
		List<Esteticista> esteticistaList = repository.findAll();

		if (esteticistaList.size() > 0) {
			return esteticistaList;
		} else {
			return new ArrayList<Esteticista>();
		}
	}

	public Esteticista getEsteticistaById(Integer id) throws Exception {
		Optional<Esteticista> esteticista = repository.findById(id);

		if (esteticista.isPresent()) {
			return esteticista.get();
		} else {
			throw new Exception("No esteticista record exist for given id");
		}
	}

	public void deleteEsteticistaById(Integer id) throws Exception {
		Optional<Esteticista> esteticista = repository.findById(id);

		if (esteticista.isPresent()) {
			if(!esteticista.get().getCitas().isEmpty()) {
				for(Cita c : esteticista.get().getCitas()) {
					c.setEsteticista(null);
					citaRepo.saveAndFlush(c);
				}
				}
			
			for(ServicioEstetico serv : servicioEstetico.getAllServicioEsteticos()) {
				if(serv.getEsteticista().contains(esteticista.get())) {
					serv.getEsteticista().remove(esteticista.get());
				}
			}
			
			repository.deleteById(id);
		} else {
			throw new Exception("No esteticista record exist for given id");
		}
	}

}
