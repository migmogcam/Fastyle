package app.fastyleapplication.fastyle.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {
		
		private Integer id;
			
		@NotBlank
		@Length(min = 5,max = 20)
		private String usuario;
		
		@NotBlank
		@Length(min = 5,max = 20)
		private String password;
		
		@NotBlank
		private String password2;

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
		
		@Range(min = 18)
		private Integer edad;
		
		@NotBlank
		@Length(max = 100)
		@Email
		private String eMail;
		
		@Length(max = 250)
		private String descripcion;
		
		@NotBlank
		@Length(max = 100)
		private String autority;
		
		@URL
		private String imagen1;
		@URL
		private String imagen2;
		@URL
		private String imagen3;
		@URL
		private String imagen4;
		
	
}
