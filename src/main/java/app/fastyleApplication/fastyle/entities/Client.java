//package app.fastyleapplication.fastyle.entities;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.validation.constraints.NotBlank;
//
//@Entity
//public class Client {
//    
//  
//    private long id;
//   
//    private String name;
//    
//   
//    private String apellido1;
//    
//    
//    private String apellido2;
//    
//   
//    private String email;
//    
// 
//    private String username;
//    
//    public String getApellido1() {
//		return apellido1;
//	}
//
//	public void setApellido1(String apellido1) {
//		this.apellido1 = apellido1;
//	}
//
//	public String getApellido2() {
//		return apellido2;
//	}
//
//	public void setApellido2(String apellido2) {
//		this.apellido2 = apellido2;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getProvincia() {
//		return provincia;
//	}
//
//	public void setProvincia(String provincia) {
//		this.provincia = provincia;
//	}
//
//	public String getCiudad() {
//		return ciudad;
//	}
//
//	public void setCiudad(String ciudad) {
//		this.ciudad = ciudad;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	@NotBlank
//    private String provincia;
//    
//    
//    @NotBlank
//    private String ciudad;
//    
//    
//    @NotBlank
//    private String password;
//
//    public Client() {}
//
//    public Client(String name, String email) {
//        this.name = name;
//        this.email = email;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//    
//    public long getId() {
//        return id;
//    }
//    
//    public void setName(String name) {
//        this.name = name;
//    }
//    
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    @Override
//    public String toString() {
//        return "Client{" + "id=" + id + ", name=" + name + ", email=" + email + '}';
//    }
//}
