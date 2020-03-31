package app.fastyleApplication.fastyle.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fastyleApplication.fastyle.model.Cita;
import app.fastyleApplication.fastyle.model.Esteticista;
import app.fastyleApplication.fastyle.model.ServicioEstetico;
import app.fastyleApplication.fastyle.repository.CitaRepository;

@Service
public class CitaService {

	@Autowired
	CitaRepository repository;
	
	@Autowired
	EsteticistaService esteticistaService;
	
	@Autowired
	ServicioEsteticoService servicioEsteticistaService;
	
	@Autowired
	ClienteService clienteService;

	public List<Cita> getAllCitas() {
		List<Cita> citaList = repository.findAll();

		if (citaList.size() > 0) {
			return citaList;
		} else {
			return new ArrayList<Cita>();
		}
	}

	public Cita getCitaById(Integer id) throws Exception {
		Optional<Cita> cita = repository.findById(id);

		if (cita.isPresent()) {
			return cita.get();
		} else {
			throw new Exception("No cita record exist for given id");
		}
	}

	public Cita createOrUpdateCita(Cita entity) throws Exception {
		if(entity.getId() != null) {
		Optional<Cita> cita = repository.findById(entity.getId());
			Cita newEntity = cita.get();
			newEntity.setCliente(entity.getCliente());
			newEntity.setFecha(entity.getFecha());
			newEntity.setHora(entity.getHora());
			newEntity.setServicioEstetico(entity.getServicioEstetico());
			newEntity.setDetalle(entity.getDetalle());
			newEntity.setEsteticista(entity.getEsteticista());
			newEntity = repository.save(newEntity);

			return newEntity;
		} else {
			entity = repository.save(entity);
			return entity;
		}
	}

	public void deleteCitaById(Integer id) throws Exception {
		Optional<Cita> cita = repository.findById(id);

		if (cita.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new Exception("No cita record exist for given id");
		}
	}

}
