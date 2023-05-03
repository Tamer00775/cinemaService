package kz.kartayev.cinema.controllers;

import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for Movie.
 * */
@RestController
@RequestMapping("/movies")
public class MovieController {
  private final MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  /**
   * Get description about movie.
   * */
  @GetMapping("/{id}")
  public Movie index(@PathVariable("id") int id) {
    return movieService.index(id);
  }
}
