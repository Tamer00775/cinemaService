package kz.kartayev.cinema.util;

import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserValidator implements Validator {
  private final PersonService personService;
  @Autowired
  public UserValidator(PersonService personService) {
    this.personService = personService;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return false;
  }

  @Override
  public void validate(Object target, Errors errors) {
    Person person = (Person) target;
    List<Person> personList = personService.findAll();
    int count = (int) personList.stream().filter(a -> a.getUsername()
            .equals(person.getUsername())).count();
    if(person.getCard().isEmpty() || person.getCard().length() < 16)
      errors.rejectValue("card", "","Your card have incorrect values!");
    if(count > 1)
      errors.rejectValue("username", "", "This username is already exists");
  }
}
