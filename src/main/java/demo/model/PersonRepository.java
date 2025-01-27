package demo.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    Person findById(long id);
    Person findByUsername(String username);

    List<Person> findByRoleIn(List<Role> administrator);

    List<Person> findByRole(Role supervisor);
    List<Person> findAll();
}
