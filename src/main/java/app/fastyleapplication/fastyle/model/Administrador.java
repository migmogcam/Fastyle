package app.fastyleapplication.fastyle.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Administrador extends BaseEntity{
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
	@Getter
	@Setter
	private Usuario usuario;

}

