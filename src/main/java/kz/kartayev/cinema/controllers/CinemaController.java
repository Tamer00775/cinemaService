package kz.kartayev.cinema.controllers;

import java.util.Date;
import java.util.List;
import kz.kartayev.cinema.model.CinemaCenter;
import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for cinema url.
 * */
@RestController
@RequestMapping("/cinema")
public class CinemaController {
  private final CinemaService cinemaService;

  @Autowired
  public CinemaController(CinemaService cinemaService) {
    this.cinemaService = cinemaService;
  }

  /**
   * Get all cinema center.
   * */
  @GetMapping
  public List<CinemaCenter> index() {
    return cinemaService.show();
  }

  /**
   * Get all movies from cinema center in today.
   * */
  @GetMapping("/{id}")
  public List<Movie> movies(@PathVariable("id") int id) {
    return cinemaService.schedule(id, new Date(System.currentTimeMillis()));
  }

  /**
   * Get all movies from cinema center in tomorrow.
   * */
  // TODO : FIX THIS METHOD
  @GetMapping("/{id}/tomorrow")
  public List<Movie> tomorrowMovies(@PathVariable("id") int id) {
    Date dt = new Date(System.currentTimeMillis());
    dt.setHours(dt.getHours() + 24);
    return cinemaService.schedule(id, dt);
  }

}
