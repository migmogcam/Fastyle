package app.fastyleApplication.fastyle.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Data
@Entity
public class Cliente extends Usuario{

//	public Usuario(String usuario2, String password2, List grantList) {
//		// TODO Auto-generated constructor stub
//		this.usuario = usuario;
//		this.password = password;
//		this.authorities = authorities;
//	}
	
	
	@Getter @Setter
	@JoinTable(name="authorities_users", joinColumns= @JoinColumn(name="userId"), inverseJoinColumns = @JoinColumn(name="authorityId"))
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authorities> authorities;
	
	public Cliente(String usuario, String password, List<Authorities> grantList) {
		super(usuario, password); 
		this.authorities = grantList;
	}

	public Cliente() {
		super();
		
	}
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Cita> citas;

}
