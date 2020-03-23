package app.fastyleApplication.fastyle.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Esteticista extends Usuario {
	
	@OneToMany(cascade = CascadeType.ALL)
	@Getter
	@Setter
	private List<Cita> citas;

}
