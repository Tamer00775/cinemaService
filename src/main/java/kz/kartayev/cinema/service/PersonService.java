package kz.kartayev.cinema.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.repository.PersonRepository;
import kz.kartayev.cinema.security.PersonDetails;
import kz.kartayev.cinema.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
  private final PersonRepository personRepository;

  @Autowired
  public PersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public Person getUserByName(String username){
    Pattern pattern = Pattern.compile("\\d");
    Matcher matcher = pattern.matcher(username);
    if(matcher.find())
      throw new ErrorMessage("Your username have digits");
    else
      return personRepository.getPersonByUsername(username);
  }
  public List<Person> findAll(){
    return personRepository.findAll();
  }
  public Person show(int id) throws Exception {
    Optional<Person> person = personRepository.findById(id);
    if(person.isEmpty())
      throw new Exception("Not found");
    return person.get();
  }
  @Transactional
  public void save(Person person){
    personRepository.save(person);
  }
  public Person getInfo(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    return personDetails.getPerson();
  }

  @Transactional
  public void changePassword(Person person, String newPassword) {
    person.setPassword(newPassword);
    personRepository.save(person);
  }
  @Transactional
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public void delete(int personId){
    personRepository.deleteById(personId);
  }
}
