package kz.kartayev.cinema.service;

import java.util.Optional;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.repository.PersonRepository;
import kz.kartayev.cinema.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * PersonDetailsService class for using Spring security.
 */
@Service
public class PersonDetailsService implements UserDetailsService {
  private final PersonRepository personRepository;

  @Autowired
  public PersonDetailsService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  /**
   * Get user from database with username.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Person> person = personRepository.findByUsername(username);
    if (person.isEmpty()) {
      throw new UsernameNotFoundException("not found");
    }
    return new PersonDetails(person.get());
  }
}
