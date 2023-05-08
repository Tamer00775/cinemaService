package kz.kartayev.cinema.service;

import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.repository.PersonRepository;
import kz.kartayev.cinema.util.ErrorMessage;
import kz.kartayev.cinema.util.UserValidator;
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
  private final UserValidator userValidator;

  @Autowired
  public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder, UserValidator userValidator) {
    this.personRepository = personRepository;
    this.passwordEncoder = passwordEncoder;
    this.userValidator = userValidator;
  }

  /**
   * Method for register person and encode password.
   * */
  @Transactional
  public void register(Person person) {
    if(person.getPassword().length() < 8 )
      throw new ErrorMessage("Your password is incorrect");
    if(person.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$"))
      throw new ErrorMessage("Your password must be in format: Tamerlan123");
    if(person.getCard().length() < 16)
      throw new ErrorMessage("Your card is incorrect format!");
    person.setPassword(passwordEncoder.encode(person.getPassword()));
    person.setRole("ROLE_USER");
    personRepository.save(person);
  }

}
