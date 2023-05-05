package kz.kartayev.cinema.controllers;

import java.util.List;

import kz.kartayev.cinema.dto.MoneyDto;
import kz.kartayev.cinema.dto.PersonDto;
import kz.kartayev.cinema.model.Comment;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.service.PersonService;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  @GetMapping("/mycomments")
  public List<Comment> myComments() {
     return personService.getInfo().getComments();
  }
  /**
   * Set new money.
   * */
  @PostMapping("/money")
  public ResponseEntity<HttpStatus> addMoney(@RequestBody MoneyDto money){
    // TODO : Check if bank card is filled.
    Person person = personService.getInfo();
    person.setWallet(person.getWallet() + money.getTotalMoney());
    personService.save(person);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Method for map to Dto.
   * */
  public PersonDto toPersonDto(Person person) {
    return modelMapper.map(person, PersonDto.class);
  }
}
