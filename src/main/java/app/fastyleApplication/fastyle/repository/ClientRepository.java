package app.fastyleApplication.fastyle.repository;

import app.fastyleApplication.fastyle.entities.Client;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    
    List<Client> findByName(String name);
    
}
