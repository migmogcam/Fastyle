package app.fastyleApplication.fastyle.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Range;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Cliente extends BaseEntity{

	
	
	@OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Cita> citas;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
	@Getter
	@Setter
	private Usuario usuario;
	
	@Range(min = 0)
	private Double puntos;

}
