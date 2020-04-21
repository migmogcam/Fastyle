package app.fastyleapplication.fastyle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.fastyleapplication.fastyle.model.Administrador;


@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Integer>{

}
