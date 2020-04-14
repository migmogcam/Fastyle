package app.fastyleApplication.fastyle.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Data
@Entity
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
	@Length(max = 200)
	private String direccion;
	
	@NotBlank
	@Length(max = 100)
	@Email
	private String eMail;
	
	@Range(min = 18)
	private Integer edad;
	
	@Getter @Setter
	@JoinTable(name="authorities_users", joinColumns= @JoinColumn(name="userId"), inverseJoinColumns = @JoinColumn(name="authorityId"))
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
	@Valid
    private List<Authority> authorities;

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
	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	
}

