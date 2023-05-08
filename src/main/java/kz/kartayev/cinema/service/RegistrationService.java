package kz.kartayev.cinema.service;

import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.repository.PersonRepository;
import kz.kartayev.cinema.util.ErrorMessage;
import kz.kartayev.cinema.util.ErrorResponse;
import kz.kartayev.cinema.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static kz.kartayev.cinema.util.ErrorUtil.getFieldErrors;

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
    person.setPassword(passwordEncoder.encode(person.getPassword()));
    person.setRole("ROLE_USER");
    if(person.getPassword().length() < 5 )
      throw new ErrorMessage("Your password is incorrect");
    personRepository.save(person);
  }

}
