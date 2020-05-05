package app.fastyleapplication.fastyle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.fastyleapplication.fastyle.model.Cliente;
import app.fastyleapplication.fastyle.model.Usuario;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	public Optional<Cliente> findByUsuario(Usuario usuario);
}
