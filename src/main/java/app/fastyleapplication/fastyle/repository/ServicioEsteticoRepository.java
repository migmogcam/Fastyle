package app.fastyleapplication.fastyle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.fastyleapplication.fastyle.model.ServicioEstetico;


@Repository
public interface ServicioEsteticoRepository extends JpaRepository<ServicioEstetico, Integer>{

	List<ServicioEstetico> findByProvincia(String provincia);
	
	List<ServicioEstetico> findByTipo(String tipo);
}
