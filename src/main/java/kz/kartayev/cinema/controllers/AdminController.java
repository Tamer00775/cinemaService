package kz.kartayev.cinema.controllers;

import static kz.kartayev.cinema.util.ErrorUtil.getFieldErrors;

import java.util.List;
import javax.validation.Valid;
import kz.kartayev.cinema.dto.CinemaDto;
import kz.kartayev.cinema.dto.MovieDto;
import kz.kartayev.cinema.model.CinemaCenter;
import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.service.AdminService;
import kz.kartayev.cinema.service.CinemaService;
import kz.kartayev.cinema.service.PersonService;
import kz.kartayev.cinema.util.ErrorMessage;
import kz.kartayev.cinema.util.ErrorResponse;
import kz.kartayev.cinema.util.MovieValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for admin.
 * */
@RestController
@RequestMapping("/admin")
public class AdminController {
  private final PersonService personService;
  private final ModelMapper modelMapper;
  private final MovieValidator movieValidator;
  private final AdminService adminService;

  /**
   * Admin controller.
   * */
  @Autowired
  public AdminController(PersonService personService,
                         ModelMapper modelMapper,
                         MovieValidator movieValidator, AdminService adminService) {
    this.personService = personService;
    this.modelMapper = modelMapper;
    this.movieValidator = movieValidator;
    this.adminService = adminService;
  }

  /**
   * Get all user in system.
   * */
  @GetMapping
  public List<Person> allUser() {
    return personService.findAll();
  }

  /**
   * Get user by id.
   * */
  @GetMapping("/user/{id}")
  public Person getUser(@PathVariable("id") int userId) throws Exception {
    return personService.show(userId);
  }

  /**
   * Delete user by id.
   * */
  @DeleteMapping("/user/{id}/delete")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int userId) {
    personService.delete(userId);
    return ResponseEntity.ok(HttpStatus.ACCEPTED);
  }

  /**
   * manage admin access in system.
   * */
  @PostMapping("/user/{id}/manage")
  public ResponseEntity<HttpStatus> manageAccessToAdmin(@PathVariable("id")
                                                          int userId) throws Exception {
    Person person = personService.show(userId);
    person.setRole("ROLE_ADMIN");
    personService.save(person);
    return ResponseEntity.ok(HttpStatus.ACCEPTED);
  }

  /**
   * Adding new cinema center.
   * */
  @PostMapping("/cinema/add")
  public ResponseEntity<HttpStatus> addCinemaCenter(@RequestBody @Valid CinemaDto cinemaDto,
                                                    BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      getFieldErrors(bindingResult);
    }
    CinemaCenter cinemaCenter = toCinema(cinemaDto);
    adminService.saveCinema(cinemaCenter);
    return ResponseEntity.ok(HttpStatus.ACCEPTED);
  }

  // TODO : ADD EXCEPTION HANDLER
  /**
   * Add new movie.
   * */
  @PostMapping("/cinema/{id}/add")
  public ResponseEntity<HttpStatus> addMovie(@PathVariable("id") int cinemaId,
                                             @RequestBody @Valid MovieDto movieDto,
                                             BindingResult bindingResult) {
    Movie movie = toMovie(movieDto);
    movieValidator.validate(movie, bindingResult);
    if(bindingResult.hasErrors()){
      getFieldErrors(bindingResult);
    }
    adminService.saveMovie(movie, cinemaId);
    return ResponseEntity.ok(HttpStatus.OK);
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

  /**
   * Transform to Movie from MovieDto.
   * */
  private Movie toMovie(MovieDto movieDto) {
    return modelMapper.map(movieDto, Movie.class);
  }

  /**
   * Transform to CinemaCenter from CinemaCenterDto.
   * */
  private CinemaCenter toCinema(CinemaDto cinemaDto) {
    return modelMapper.map(cinemaDto, CinemaCenter.class);
  }
}

