package app.fastyleapplication.fastyle.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fastyleapplication.fastyle.model.Cita;
import app.fastyleapplication.fastyle.model.ServicioEstetico;
import app.fastyleapplication.fastyle.repository.ServicioEsteticoRepository;

@Service
public class ServicioEsteticoService {

	@Autowired
	ServicioEsteticoRepository repository;

	public List<ServicioEstetico> getAllServicioEsteticos() {
		List<ServicioEstetico> servicioEsteticoList = repository.findAll();

		if (!servicioEsteticoList.isEmpty()) {
			return servicioEsteticoList;
		} else {
			return new ArrayList<>();
		}
	}
	
	public List<ServicioEstetico> getAllServicioEsteticosPorProvincia(String provincia) {
		List<ServicioEstetico> servicioEsteticoList = repository.findByProvincia(provincia);

		if (!servicioEsteticoList.isEmpty()) {
			return servicioEsteticoList;
		} else {
			return new ArrayList<>();
		}
	}
	
	public List<ServicioEstetico> getAllServicioEsteticosPorTipo(String tipo) {
		List<ServicioEstetico> servicioEsteticoList = repository.findByTipo(tipo);

		if (!servicioEsteticoList.isEmpty()) {
			return servicioEsteticoList;
		} else {
			return new ArrayList<>();
		}
	}
	
	public List<ServicioEstetico> getAllServicioEsteticosPorProvinciaYTipo(String provincia, String tipo) {
		List<ServicioEstetico> res = new ArrayList<ServicioEstetico>();
		List<ServicioEstetico> servicioEsteticoList = repository.findAll();
		for(ServicioEstetico se: servicioEsteticoList) {
			if(se.getProvincia().equals(provincia) && se.getTipo().equals(tipo)) {
				res.add(se);
			}
		}
		if (!res.isEmpty()) {
			return res;
		} else {
			return new ArrayList<>();
		}
	}

	public ServicioEstetico getServicioEsteticoById(Integer id) throws IllegalArgumentException {
		Optional<ServicioEstetico> servicioEstetico = repository.findById(id);

		if (servicioEstetico.isPresent()) {
			return servicioEstetico.get();
		} else {
			throw new IllegalArgumentException("No servicioEstetico record exist for given id");
		}
	}

	public ServicioEstetico createOrUpdateServicioEstetico(ServicioEstetico entity) throws IllegalArgumentException {
		if(entity.getId() != null) {
			Optional<ServicioEstetico> servicioEstetico = repository.findById(entity.getId());
			ServicioEstetico newEntity = new ServicioEstetico();
			if(servicioEstetico.isPresent()) {
				newEntity = servicioEstetico.get();
				newEntity.setCitas(entity.getCitas());
				newEntity.setDetalle(entity.getDetalle());
				newEntity.setDetalleAcortado(entity.getDetalleAcortado());
				newEntity.setCiudad(entity.getCiudad());
				newEntity.setProvincia(entity.getProvincia());
				newEntity.setImagen(entity.getImagen());
				newEntity.setPrecio(entity.getPrecio());
				newEntity.setTipo(entity.getTipo());
				newEntity.setEsteticista(entity.getEsteticista());
			}
			newEntity = repository.save(newEntity);

			return newEntity;
		} else {
			entity.setCitas(new LinkedList<>());
			entity = repository.save(entity);

			return entity;
		}
	}

	public void deleteServicioEsteticoById(Integer id) throws IllegalArgumentException {
		Optional<ServicioEstetico> servicioEstetico = repository.findById(id);

		if (servicioEstetico.isPresent()) {
			for(Cita cita : servicioEstetico.get().getCitas()) {
				cita.setServicioEstetico(null);
			}
			
			servicioEstetico.get().setCitas(null);
			servicioEstetico.get().setEsteticista(null);
			repository.deleteById(id);
			
		} else {
			throw new IllegalArgumentException("No servicioEstetico record exist for given id");
		}
	}

}
