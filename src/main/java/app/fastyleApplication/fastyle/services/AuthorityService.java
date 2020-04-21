package app.fastyleApplication.fastyle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fastyleApplication.fastyle.model.Authority;
import app.fastyleApplication.fastyle.repository.AuthorityRepository;

@Service
public class AuthorityService {
	
    @Autowired
    AuthorityRepository repository;
    
    public Authority findByAuthority(String auto) {
    	return repository.findByAuthority(auto);
    }
     
 

}
