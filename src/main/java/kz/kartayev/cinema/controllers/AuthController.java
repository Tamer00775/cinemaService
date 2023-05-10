package kz.kartayev.cinema.controllers;

import static kz.kartayev.cinema.util.ErrorUtil.getFieldErrors;

import javax.validation.Valid;
import kz.kartayev.cinema.dto.PersonDto;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.service.RegistrationService;
import kz.kartayev.cinema.util.ErrorMessage;
import kz.kartayev.cinema.util.ErrorResponse;
import kz.kartayev.cinema.util.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
  private final UserValidator userValidator;

  /**
   * Authentication controller.
   * */
  @Autowired
  public AuthController(RegistrationService registrationService,
                        ModelMapper modelMapper, UserValidator userValidator) {
    this.registrationService = registrationService;
    this.modelMapper = modelMapper;
    this.userValidator = userValidator;
  }

  /**
   * registration to system.
   */
  @PostMapping("/registration")
  public ResponseEntity<HttpStatus> performRegistration(@RequestBody @Valid PersonDto personDto,
                                                        BindingResult bindingResult) {
    userValidator.validate(toPerson(personDto), bindingResult);
    if (bindingResult.hasErrors()) {
      getFieldErrors(bindingResult);
    }
    registrationService.register(toPerson(personDto));
    return ResponseEntity.ok(HttpStatus.ACCEPTED);
  }


  /**
   * Mapping from PersonDto to Person.
   * */
  public Person toPerson(PersonDto personDto) {
    return modelMapper.map(personDto, Person.class);
  }

  /**
   * Handler for exceptions.
   * */
  @ExceptionHandler
  public ResponseEntity<ErrorResponse> exceptionHandler(ErrorMessage errorMessage) {
    ErrorResponse errorResponse = new ErrorResponse(errorMessage.getMessage(),
            System.currentTimeMillis());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
