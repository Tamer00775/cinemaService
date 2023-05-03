package kz.kartayev.cinema.service;

import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MovieService {
  private final MovieRepository movieRepository;
  @Autowired
  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  //TODO: update this index() method
  public Movie index(int id){
    Optional<Movie> movie = movieRepository.findById(id);
    return movie.get();
  }

  @Transactional
  public void save(Movie movie){
    movieRepository.save(movie);
  }
}
