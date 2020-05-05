package app.fastyleapplication.fastyle.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fastyleapplication.fastyle.model.Cita;
import app.fastyleapplication.fastyle.repository.CitaRepository;

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

		if (!citaList.isEmpty()) {
			return citaList;
		} else {
			return new ArrayList<>();
		}
	}

	public Cita getCitaById(Integer id) {
		Optional<Cita> cita = repository.findById(id);

		if (cita.isPresent()) {
			return cita.get();
		} else {
			throw new IllegalArgumentException("No cita record exist for given id");
		}
	}

	public void createOrUpdateCita(Cita entity) {
		if(entity.getId() != null) {
			Optional<Cita> cita = repository.findById(entity.getId());
			Cita newEntity;
			if(cita.isPresent()) {
				newEntity = cita.get();
				newEntity.setFecha(entity.getFecha());
				newEntity.setHora(entity.getHora());
				newEntity.setDetalle(entity.getDetalle());
				newEntity.setEstado(entity.getEstado());
				newEntity.setRespuesta(entity.getRespuesta());
				newEntity.setMomento(entity.getMomento());
				newEntity.setValorar(entity.getValorar());
				newEntity.setFValorar(entity.getFValorar());
				repository.save(newEntity);
			}
		} else {
			repository.save(entity);
		}
	}

	public void deleteCitaById(Integer id) {
		Optional<Cita> cita = repository.findById(id);

		if (cita.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new IllegalArgumentException("No cita record exist for given id");
		}
	}

}
