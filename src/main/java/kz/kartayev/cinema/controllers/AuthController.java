package kz.kartayev.cinema.controllers;

import static kz.kartayev.cinema.util.ErrorUtil.getFieldErrors;

import javax.validation.Valid;

import kz.kartayev.cinema.dto.AuthenticationDTO;
import kz.kartayev.cinema.dto.PersonDto;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.security.JWTUtil;
import kz.kartayev.cinema.service.RegistrationService;
import kz.kartayev.cinema.util.ErrorMessage;
import kz.kartayev.cinema.util.ErrorResponse;
import kz.kartayev.cinema.util.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Authentication controller for user.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
  private final RegistrationService registrationService;
  private final ModelMapper modelMapper;
  private final UserValidator userValidator;
  private final AuthenticationManager authenticationManager;
  private final JWTUtil jwtUtil;

  /**
   * Authentication controller.
   * */
  @Autowired
  public AuthController(RegistrationService registrationService,
                        ModelMapper modelMapper, UserValidator userValidator, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
    this.registrationService = registrationService;
    this.modelMapper = modelMapper;
    this.userValidator = userValidator;
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
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
   * Login to system.
   * */
  @PostMapping("/login")
  public Map<String, String> login(@RequestBody AuthenticationDTO authenticationDTO, BindingResult bindingResult){
    if(bindingResult.hasErrors()){
      getFieldErrors(bindingResult);
    }
    UsernamePasswordAuthenticationToken authInputToken =
            new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                    authenticationDTO.getPassword());
    try {
      authenticationManager.authenticate(authInputToken);
    } catch (BadCredentialsException e) {
      return Map.of("message", "incorrect credentials!");
    }
    String token = jwtUtil.generateToken(authenticationDTO.getUsername());
    return Map.of("jwt-token",token);
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
