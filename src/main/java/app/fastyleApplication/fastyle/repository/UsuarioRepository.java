package app.fastyleApplication.fastyle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.fastyleApplication.fastyle.model.Cliente;
import app.fastyleApplication.fastyle.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	public Optional<Usuario> findByUsuario(String usuario);
}
