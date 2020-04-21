package app.fastyleapplication.fastyle.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Esteticista extends BaseEntity {
	
	@OneToMany(mappedBy = "esteticista", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@Getter
	@Setter
	@Valid
	private List<Cita> citas;
	
	@Getter
	@Setter
	private String descripcion;
	
	@Getter
	@Setter
	@ElementCollection
	@CollectionTable(name="imagenes", joinColumns=@JoinColumn(name="esteticista_id"))
	@Column(name="imagenes")
	private List<String> imagenes;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
	@Getter
	@Setter
	private Usuario usuario;

}
