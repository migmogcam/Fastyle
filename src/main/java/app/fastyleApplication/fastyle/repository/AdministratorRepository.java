package app.fastyleApplication.fastyle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.fastyleApplication.fastyle.model.Administrador;


@Repository
public interface AdministratorRepository extends JpaRepository<Administrador, Integer>{

}
