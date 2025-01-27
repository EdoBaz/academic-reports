package demo.model;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findByName(String name);
    Project findById(long id);

    List<Project> findBySupervisor(Person supervisor);
    // Trova progetti dove l'utente è supervisore o ricercatore
    List<Project> findBySupervisorOrResearchers(Person supervisor, Person researcher);

    // Trova progetti dove l'utente è ricercatore
    List<Project> findByResearchers(Person researcher);


}
