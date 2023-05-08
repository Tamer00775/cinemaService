package kz.kartayev.cinema.repository;

import java.util.Optional;
import kz.kartayev.cinema.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Person class.
 * */
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
  Optional<Person> findByUsername(String s);

  Person getPersonByUsername(String s);
}
