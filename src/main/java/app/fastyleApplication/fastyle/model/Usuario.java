package app.fastyleApplication.fastyle.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@MappedSuperclass
public class Usuario{
	
	@Id
	@NotBlank
	@Length(max = 15)
	private String usuario;
	
	@NotBlank
	@Length(max = 15)
	private String password;

	@NotBlank
	@Length(max = 100)
	private String name;
	
	@NotBlank
	@Length(max = 100)
	private String apellido1;
	
	@NotBlank
	@Length(max = 100)
	private String apellido2;
	
	

	
}
