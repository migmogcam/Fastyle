package app.fastyleApplication.fastyle.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@MappedSuperclass
public class Usuario{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
		
	@NotBlank
	@Length(max = 200)
	private String usuario;
	
	@NotBlank
	@Length(max = 200)
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
	
	@NotBlank
	@Length(max = 100)
	private String provincia;
	
	@NotBlank
	@Length(max = 100)
	 private String ciudad;
	
	@NotBlank
	@Length(max = 100)
	@Email
	private String eMail;

	public Usuario() {}

	public Usuario(String usuario, String passwordS) {
		this.usuario = usuario; 
		this.password = password;
		
		
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
	
	

	
}

