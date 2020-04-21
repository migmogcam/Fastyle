package app.fastyleapplication.fastyle.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "authorities")
public class Authority {
	@Id
	@Getter @Setter String username;
	@Getter @Setter String authority;
}
