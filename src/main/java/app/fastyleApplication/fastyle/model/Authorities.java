package app.fastyleApplication.fastyle.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "authorities")
public class Authorities {
	@Id
	String username;
	String authority;
}
