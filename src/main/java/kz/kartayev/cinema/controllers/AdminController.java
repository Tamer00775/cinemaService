package kz.kartayev.cinema.controllers;

import java.util.List;
import javax.validation.Valid;
import kz.kartayev.cinema.dto.CinemaDto;
import kz.kartayev.cinema.dto.MovieDto;
import kz.kartayev.cinema.model.CinemaCenter;
import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.service.CinemaService;
import kz.kartayev.cinema.service.MovieService;
import kz.kartayev.cinema.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static kz.kartayev.cinema.util.ErrorUtil.getFieldErrors;
@RestController
@RequestMapping("/admin")
public class AdminController {
  private final PersonService personService;
  private final CinemaService cinemaService;
  private final ModelMapper modelMapper;
  private final MovieService movieService;
  @Autowired
  public AdminController(PersonService personService, CinemaService cinemaService, ModelMapper modelMapper, MovieService movieService) {
    this.personService = personService;
    this.cinemaService = cinemaService;
    this.modelMapper = modelMapper;
    this.movieService = movieService;
  }
  @GetMapping
  public List<Person> allUser(){
    return personService.findAll();
  }
  @GetMapping("/user/{id}")
  public Person getUser(@PathVariable("id") int userId) throws Exception {
    return personService.show(userId);
  }
  @DeleteMapping("/user/{id}/delete")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int userId){
    personService.delete(userId);
    return ResponseEntity.ok(HttpStatus.ACCEPTED);
  }
  @PostMapping("/user/{id}/manage")
  public ResponseEntity<HttpStatus> manageAccessToAdmin(@PathVariable("id") int userId) throws Exception {
    Person person = personService.show(userId);
    person.setRole("ROLE_ADMIN");
    personService.save(person);
    return ResponseEntity.ok(HttpStatus.ACCEPTED);
  }
  @PostMapping("/cinema/add")
  public ResponseEntity<HttpStatus> addCinemaCenter(@RequestBody @Valid CinemaDto cinemaDto,
                                                    BindingResult bindingResult){
    if(bindingResult.hasErrors())
      getFieldErrors(bindingResult);
    CinemaCenter cinemaCenter = toCinema(cinemaDto);
    cinemaService.saveCinemaCenter(cinemaCenter);
    return ResponseEntity.ok(HttpStatus.ACCEPTED);
  }
  @PostMapping("/cinema/{id}/add")
  public ResponseEntity<HttpStatus> addMovie(@PathVariable("id") int cinema_id,
                                             @RequestBody MovieDto movieDto){
    System.out.println(movieDto);
    Movie movie = toMovie(movieDto);
    movie.setCinemaCenter(cinemaService.index(cinema_id));
    movie.setPlaces(30);
    movie.setRaiting(5.0);
    movie.setPrice(3000);
    movieService.save(movie);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  private Movie toMovie(MovieDto movieDto){
    return modelMapper.map(movieDto, Movie.class);
  }
  private CinemaCenter toCinema(CinemaDto cinemaDto){
    return modelMapper.map(cinemaDto, CinemaCenter.class);
  }
}

