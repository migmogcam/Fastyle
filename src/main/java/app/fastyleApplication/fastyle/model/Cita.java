package app.fastyleApplication.fastyle.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

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

}
