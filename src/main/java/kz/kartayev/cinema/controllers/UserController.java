package kz.kartayev.cinema.controllers;

import kz.kartayev.cinema.dto.PersonDto;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.security.PersonDetails;
import kz.kartayev.cinema.service.PersonDetailsService;
import kz.kartayev.cinema.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user in system.
 * */
@RestController
@RequestMapping("/user")
public class UserController {
  private final PersonService personService;
  private final ModelMapper modelMapper;

  @Autowired
  public UserController(PersonService personService, ModelMapper modelMapper) {
    this.personService = personService;
    this.modelMapper = modelMapper;
  }

  /**
   * Get user information.
   * */
  @GetMapping
  public Person getInfo(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    return personDetails.getPerson();
  }

  /**
   * Method for map to Dto.
   * */
  public PersonDto toPersonDto(Person person) {
    return modelMapper.map(person, PersonDto.class);
  }
}
