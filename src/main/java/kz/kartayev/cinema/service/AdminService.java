package kz.kartayev.cinema.service;

import kz.kartayev.cinema.model.CinemaCenter;
import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Date;

@Service
public class AdminService {
  private final MovieService movieService;
  private final CinemaService cinemaService;
  @Autowired
  public AdminService(MovieService movieService, CinemaService cinemaService) {
    this.movieService = movieService;
    this.cinemaService = cinemaService;
  }

  @Transactional
  public void saveCinema(CinemaCenter cinemaCenter){
    cinemaService.saveCinemaCenter(cinemaCenter);
  }
  @Transactional
  public void saveMovie(Movie movie, int cinemaId)  throws ErrorMessage{
    Date date = new Date();
    if(movie.getStartDate().before(date))
      throw new ErrorMessage("Date is incorrect!");
    movie.setCinemaCenter(cinemaService.index(cinemaId));
    movie.setPlaces(30);
    movie.setRaiting(5.0);
    movie.setPrice(3000);
    movieService.save(movie);
  }

  @Transactional
  public void clearMovie(Movie movie){
    movieService.clear(movie);
  }
}
