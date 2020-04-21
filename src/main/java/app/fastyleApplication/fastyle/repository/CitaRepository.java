package app.fastyleapplication.fastyle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.fastyleapplication.fastyle.model.Cita;


@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer>{


}
