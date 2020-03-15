package app.fastyleApplication.fastyle.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "authorities")
public class Authorities {
	@Id
	@Getter @Setter String username;
	@Getter @Setter String authority;
}
