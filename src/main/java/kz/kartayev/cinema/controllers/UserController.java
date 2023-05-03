package kz.kartayev.cinema.controllers;

import java.util.List;
import kz.kartayev.cinema.dto.PersonDto;
import kz.kartayev.cinema.model.Comment;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
  public Person getInfo() {
    return personService.getInfo();
  }

  // TODO: FIX ME
  /**
   * Get comments which written by user.
   * */
  @GetMapping("/myComments")
  public List<Comment> myComments() {
    return personService.getInfo().getComments();
  }

  /**
   * Method for map to Dto.
   * */
  public PersonDto toPersonDto(Person person) {
    return modelMapper.map(person, PersonDto.class);
  }
}
