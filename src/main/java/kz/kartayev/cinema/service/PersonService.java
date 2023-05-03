package kz.kartayev.cinema.service;

import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.repository.PersonRepository;
import kz.kartayev.cinema.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
  private final PersonRepository personRepository;

  @Autowired
  public PersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public Person show(int id) throws Exception {
    Optional<Person> person = personRepository.findById(id);
    if(person.isEmpty())
      throw new Exception("Not found");
    return person.get();
  }
  public Person getInfo(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    return personDetails.getPerson();
  }
}
