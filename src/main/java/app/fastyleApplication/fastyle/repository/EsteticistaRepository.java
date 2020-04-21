package app.fastyleapplication.fastyle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.fastyleapplication.fastyle.model.Esteticista;
import app.fastyleapplication.fastyle.model.Usuario;


@Repository
public interface EsteticistaRepository extends JpaRepository<Esteticista, Integer>{

	public Optional<Esteticista> findByUsuario(Usuario usuario);
}
