package app.fastyleApplication.fastyle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.fastyleApplication.fastyle.model.Administrador;
import app.fastyleApplication.fastyle.model.Esteticista;


@Repository
public interface EsteticistaRepository extends JpaRepository<Esteticista, Integer>{

}
