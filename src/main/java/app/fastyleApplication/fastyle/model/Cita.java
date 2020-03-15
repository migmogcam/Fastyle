package app.fastyleApplication.fastyle.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Range;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Cita extends BaseEntity{
	
	@Type(type = "date")
	@Future
	@Getter @Setter private Date fecha;
	
	@Range(min = 0, max = 24, message = "La hora debe ser un número de 0 a 24")
	private Integer hora;
	
	@Range(min = 0, max = 60, message = "Los minutos deben ser un número de 0 a 60")
	@Getter @Setter	private Integer minuto;

	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "usuario", insertable = false, updatable = false)
	@Getter @Setter private Cliente cliente;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "id",insertable = false, updatable = false)
	@Getter @Setter private ServicioEstetico servicioEstetico;
	
}
