package app.fastyleApplication.fastyle.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fastyleApplication.fastyle.model.Cita;
import app.fastyleApplication.fastyle.model.ServicioEstetico;
import app.fastyleApplication.fastyle.repository.ServicioEsteticoRepository;

@Service
public class ServicioEsteticoService {

	@Autowired
	ServicioEsteticoRepository repository;

	public List<ServicioEstetico> getAllServicioEsteticos() {
		List<ServicioEstetico> servicioEsteticoList = repository.findAll();

		if (servicioEsteticoList.size() > 0) {
			return servicioEsteticoList;
		} else {
			return new ArrayList<ServicioEstetico>();
		}
	}
	
	public List<ServicioEstetico> getAllServicioEsteticosPorProvincia(String provincia) {
		List<ServicioEstetico> servicioEsteticoList = repository.findByProvincia(provincia);

		if (servicioEsteticoList.size() > 0) {
			return servicioEsteticoList;
		} else {
			return new ArrayList<ServicioEstetico>();
		}
	}
	
	public List<ServicioEstetico> getAllServicioEsteticosPorTipo(String tipo) {
		List<ServicioEstetico> servicioEsteticoList = repository.findByTipo(tipo);

		if (servicioEsteticoList.size() > 0) {
			return servicioEsteticoList;
		} else {
			return new ArrayList<ServicioEstetico>();
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
		if (res.size() > 0) {
			return res;
		} else {
			return new ArrayList<ServicioEstetico>();
		}
	}

	public ServicioEstetico getServicioEsteticoById(Integer id) throws Exception {
		Optional<ServicioEstetico> servicioEstetico = repository.findById(id);

		if (servicioEstetico.isPresent()) {
			return servicioEstetico.get();
		} else {
			throw new Exception("No servicioEstetico record exist for given id");
		}
	}

	public ServicioEstetico createOrUpdateServicioEstetico(ServicioEstetico entity) throws Exception {
		if(entity.getId() != null) {
		Optional<ServicioEstetico> servicioEstetico = repository.findById(entity.getId());

			ServicioEstetico newEntity = servicioEstetico.get();
			newEntity.setCitas(entity.getCitas());
			newEntity.setDetalle(entity.getDetalle());
			newEntity.setDetalleAcortado(entity.getDetalleAcortado());
			newEntity.setCiudad(entity.getCiudad());
			newEntity.setProvincia(entity.getProvincia());
			newEntity.setImagen(entity.getImagen());
			newEntity.setPrecio(entity.getPrecio());
			newEntity.setTipo(entity.getTipo());
			newEntity.setEsteticista(entity.getEsteticista());
			
			newEntity = repository.save(newEntity);

			return newEntity;
		} else {
			entity.setCitas(new LinkedList<>());
			entity = repository.save(entity);

			return entity;
		}
	}

	public void deleteServicioEsteticoById(Integer id) throws Exception {
		Optional<ServicioEstetico> servicioEstetico = repository.findById(id);

		if (servicioEstetico.isPresent()) {
			for(Cita cita : servicioEstetico.get().getCitas()) {
				cita.setServicioEstetico(null);
			}
			
			servicioEstetico.get().setCitas(null);
			servicioEstetico.get().setEsteticista(null);
			repository.deleteById(id);
			
		} else {
			throw new Exception("No servicioEstetico record exist for given id");
		}
	}

}
