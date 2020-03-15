package app.fastyleApplication.fastyle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.fastyleApplication.fastyle.model.ServicioEstetico;


@Repository
public interface ServicioEsteticoRepository extends JpaRepository<ServicioEstetico, Integer>{

}
