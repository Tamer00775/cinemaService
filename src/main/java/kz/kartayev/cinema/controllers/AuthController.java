package kz.kartayev.cinema.controllers;

import javax.validation.Valid;
import kz.kartayev.cinema.dto.PersonDto;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.service.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Authentication controller for user.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
  private final RegistrationService registrationService;
  private final ModelMapper modelMapper;

  @Autowired
  public AuthController(RegistrationService registrationService, ModelMapper modelMapper) {
    this.registrationService = registrationService;
    this.modelMapper = modelMapper;
  }

  /**
   * registration to system.
   */
  @PostMapping("/registration")
  public ResponseEntity<HttpStatus> performRegistration(@RequestBody @Valid PersonDto personDto,
                                                        BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      System.out.println("ERROR");
    }
    registrationService.register(toPerson(personDto));
    return ResponseEntity.ok(HttpStatus.ACCEPTED);
  }

  @PostMapping("/login")
  public void login(){

  }

  /**
   * Mapping from PersonDto to Person.
   * */
  public Person toPerson(PersonDto personDto) {
    return modelMapper.map(personDto, Person.class);
  }
}
