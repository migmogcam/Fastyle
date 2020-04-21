package app.fastyleApplication.fastyle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.fastyleApplication.fastyle.model.Cita;


@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer>{


}
