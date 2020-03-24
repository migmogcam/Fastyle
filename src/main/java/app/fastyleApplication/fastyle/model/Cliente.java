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
public class Cliente extends Usuario{

	@OneToMany(cascade =  CascadeType.MERGE)
	@Getter @Setter private List<Cita> citas;

}
