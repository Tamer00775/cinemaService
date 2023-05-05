package kz.kartayev.cinema.controllers;

import kz.kartayev.cinema.dto.CinemaDto;
import kz.kartayev.cinema.model.CinemaCenter;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.service.CinemaService;
import kz.kartayev.cinema.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
  private final PersonService personService;
  private final CinemaService cinemaService;
  private final ModelMapper modelMapper;
  @Autowired
  public AdminController(PersonService personService, CinemaService cinemaService, ModelMapper modelMapper) {
    this.personService = personService;
    this.cinemaService = cinemaService;
    this.modelMapper = modelMapper;
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
  @PostMapping("/cinema/add")
  public ResponseEntity<HttpStatus> addCinemaCenter(@RequestBody CinemaDto cinemaDto){
    CinemaCenter cinemaCenter = toCinema(cinemaDto);
    cinemaService.saveCinemaCenter(cinemaCenter);
    return ResponseEntity.ok(HttpStatus.ACCEPTED);
  }

  private CinemaCenter toCinema(CinemaDto cinemaDto){
    return modelMapper.map(cinemaDto, CinemaCenter.class);
  }
}
