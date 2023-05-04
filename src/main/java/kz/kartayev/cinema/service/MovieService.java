package kz.kartayev.cinema.service;

import kz.kartayev.cinema.model.Comment;
import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.repository.MovieRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
  private final MovieRepository movieRepository;
  private final PersonService personService;
  private final CommentService commentService;
  @Autowired
  public MovieService(MovieRepository movieRepository, PersonService personService, CommentService commentService) {
    this.movieRepository = movieRepository;
    this.personService = personService;
    this.commentService = commentService;
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

  @Transactional
  public void save(int movieId, Comment comment) {
    Movie movie = index(movieId);
    comment.setPerson(personService.getInfo());
    comment.setCreatedAt(new Date());
    comment.setMovie(movie);
    commentService.save(comment);
    double rate = ((movie.getRaiting() + comment.getRating()) / (1 + movie.getComments().size()));
    movie.setRaiting(rate);
    Hibernate.initialize(personService.getInfo());
    movieRepository.save(movie);
  }
}
