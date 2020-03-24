package app.fastyleApplication.fastyle.model;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Administrador extends Usuario{
	

	public Administrador(String usuario, String password) {
		super(usuario, password); 
	}
}

