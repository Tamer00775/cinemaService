package kz.kartayev.cinema.service;

import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Registration service for creating new account.
 * */
@Service
public class RegistrationService {
  private final PersonRepository personRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
    this.personRepository = personRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Method for register person and encode password.
   * */
  @Transactional
  public void register(Person person) {
    person.setPassword(passwordEncoder.encode(person.getPassword()));
    person.setRole("ROLE_USER");
    personRepository.save(person);
  }
}
