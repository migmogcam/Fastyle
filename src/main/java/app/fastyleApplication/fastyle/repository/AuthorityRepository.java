package app.fastyleapplication.fastyle.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.fastyleapplication.fastyle.model.Authority;



@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer>{

	public Authority findByAuthority(String authority);
}
