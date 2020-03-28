package app.fastyleApplication.fastyle.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Cita extends BaseEntity{
	
	@Length(min = 0,max = 10)
	@NotBlank
	@Getter @Setter private String fecha;
	
	@Length(min = 0,max = 5)
	@NotBlank
	@Getter @Setter
	private String hora;

	@ManyToOne(cascade = CascadeType.MERGE, optional = true)
	@JoinColumn(name = "esteticista_id", insertable = false, updatable = false)
	@Valid
	@Getter @Setter private Esteticista esteticista;
	
	@ManyToOne(cascade = CascadeType.MERGE, optional = true)
	@JoinColumn(name = "usuario", insertable = false, updatable = false)
	@Valid
	@Getter @Setter private Cliente cliente;
	
	@ManyToOne(cascade = CascadeType.MERGE, optional = true)
	@JoinColumn(name = "id",insertable = false, updatable = false)
	@Valid
	@Getter @Setter private ServicioEstetico servicioEstetico;
	
	@Length(max = 250)
	@Getter @Setter private String detalle;
	
	
}
