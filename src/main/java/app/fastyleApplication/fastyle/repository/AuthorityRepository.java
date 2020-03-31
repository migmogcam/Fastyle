package app.fastyleApplication.fastyle.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import app.fastyleApplication.fastyle.model.Authority;



@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer>{

	public Authority findByAuthority(String authority);
}
