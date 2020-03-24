package app.fastyleApplication.fastyle.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Esteticista extends Usuario {
	
	@OneToMany
	@Getter
	@Setter
	private List<Cita> citas;
	
	@Getter
	@Setter
	private String descripcion;
}
