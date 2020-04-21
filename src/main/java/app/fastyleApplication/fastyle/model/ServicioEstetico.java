package app.fastyleApplication.fastyle.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

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
	
	@NotBlank
	@Length(max = 200)
	@Getter @Setter private String detalleAcortado;
	
	@Range(min = 0)
	@Getter @Setter private Double precio;
	
	@NotBlank
	@Length(max = 50000)
	@Getter @Setter private String imagen;
	
	@NotBlank
	@Length(max = 100)
	@Getter @Setter private String provincia;
	
	@NotBlank
	@Length(max = 100)
	@Getter @Setter private String ciudad;
	
	@OneToMany(mappedBy = "servicioEstetico", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@Valid
	@Getter @Setter private List<Cita> citas;
	
	@ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "servicio_esteticistas",
			joinColumns=@JoinColumn(name="servicio_id"),
			inverseJoinColumns=@JoinColumn(name="esteticista_id"))
	private List<Esteticista> esteticista;

}
