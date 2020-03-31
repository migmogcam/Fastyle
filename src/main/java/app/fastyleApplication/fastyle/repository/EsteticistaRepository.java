package app.fastyleApplication.fastyle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.fastyleApplication.fastyle.model.Administrador;
import app.fastyleApplication.fastyle.model.Cliente;
import app.fastyleApplication.fastyle.model.Esteticista;
import app.fastyleApplication.fastyle.model.Usuario;


@Repository
public interface EsteticistaRepository extends JpaRepository<Esteticista, Integer>{

	public Optional<Esteticista> findByUsuario(Usuario usuario);
}
