package app.fastyleApplication.fastyle.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Currency;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class ServicioEstetico extends BaseEntity{

	@NotBlank
	@Length(max = 100)
	@Getter @Setter private String tipo;
	
	@NotBlank
	@Length(max = 400)
	@Getter @Setter private String detalle;
	
	@Currency(value = "EUR")
	@Range(min = 0)
	@Getter @Setter private Double precio;
	
	@NotBlank
	@URL
	@Getter @Setter private String imagen;
	
	@NotBlank
	@Length(max = 100)
	@Getter @Setter private String provincia;
	
	@NotBlank
	@Length(max = 100)
	@Getter @Setter private String ciudad;
	
	@OneToMany(cascade = CascadeType.ALL)
	@Getter @Setter private List<Cita> citas;

	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "id", insertable = false, updatable = false)
	@Getter @Setter private Esteticista esteticista;
}
