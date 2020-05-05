package app.fastyleapplication.fastyle.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1671417246199538663L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Getter
	@Setter
	private Long id;

	@Column
	@NotBlank
	@Getter
	@Setter
	@Size(min = 5, max = 8, message = "No se cumple las reglas del tamano")
	private String firstName;
	@Column
	@NotBlank
	@Getter
	@Setter
	private String lastName;
	@Column
	@NotBlank
	@Getter
	@Setter
	private String email;
	@Column
	@NotBlank
	@Getter
	@Setter
	private String username;
	@Column
	@NotBlank
	@Getter
	@Setter
	private String password;

	@Transient
	@Getter
	@Setter
	private String confirmPassword;

	@Size(min = 1)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	public User() {
		super();
	}

	public User(Long id) {
		super();
		this.id = id;
	}


}
