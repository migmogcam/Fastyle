package app.fastyleApplication.fastyle.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class Usuario{
	
	@Id
	@Getter @Setter private Integer id;
		
	@NotBlank
	@Length(max = 15)
	@Getter @Setter private String usuario;
	
	@NotBlank
	@Length(max = 15)
	@Getter @Setter private String password;

	@NotBlank
	@Length(max = 100)
	@Getter @Setter private String name;
	
	@NotBlank
	@Length(max = 100)
	@Getter @Setter private String apellido1;
	
	@NotBlank
	@Length(max = 100)
	@Getter @Setter private String apellido2;
	
	@NotBlank
	@Length(max = 100)
	@Getter @Setter private String provincia;
	
	@NotBlank
	@Length(max = 100)
	@Getter @Setter private String ciudad;
	
	@NotBlank
	@Length(max = 100)
	@Email
	@Getter @Setter private String eMail;
	
	

	
}
