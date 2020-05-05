package app.fastyleApplication.fastyle.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Cita extends BaseEntity {

	@Length(min = 0, max = 10)
	@NotBlank
	@Getter
	@Setter
	private String fecha;

	@Length(min = 0, max = 5)
	@NotBlank
	@Getter
	@Setter
	private String hora;

	@ManyToOne
	@Valid
	@Getter
	@Setter
	private Esteticista esteticista;

	@ManyToOne
	@Valid
	@Getter
	@Setter
	private Cliente cliente;

	@ManyToOne
	@Valid
	@Getter
	@Setter
	private ServicioEstetico servicioEstetico;

	@Length(max = 250)
	@Getter
	@Setter
	private String detalle;

	@Length(max = 250)
	@Getter
	@Setter
	private String respuesta;
	
	@Getter
	@Setter
	private String estado;
		
	@Getter
	@Setter
	private String momento;

	@Getter
	@Setter
	private Boolean valorar;
	
	@Getter
	@Setter
	private String fValorar;
}
