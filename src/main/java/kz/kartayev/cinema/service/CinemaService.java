package kz.kartayev.cinema.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import kz.kartayev.cinema.model.CinemaCenter;
import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Cinema Service for cinema class.
 * */
@Service
public class CinemaService {
  private final CinemaRepository cinemaRepository;

  @Autowired
  public CinemaService(CinemaRepository cinemaRepository) {
    this.cinemaRepository = cinemaRepository;
  }

  /**
   * Get all Cinema center in database.
   * */
  public List<CinemaCenter> show() {
    return cinemaRepository.findAll();
  }

  /**
   * Get all movie in cinema center id.
   * */
  // TODO: Исправить гет метод.
  public List<Movie> movie(int id) {
    return cinemaRepository.findById(id).get().getMovieList();
  }

  /**
   * Get all movies in today.
   * */
  public List<Movie> schedule(int id, Date date) {
    List<Movie> movies = movie(id);
    if (!movies.isEmpty()) {
      movies = movies.stream().filter(a -> a.getStartDate().getDay() == date.getDay())
              .collect(Collectors.toList());
    }
    return movies;
  }
  public void saveCinemaCenter(CinemaCenter cinema){
    cinemaRepository.save(cinema);
  }
}
