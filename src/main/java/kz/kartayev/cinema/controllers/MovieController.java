package kz.kartayev.cinema.controllers;

import java.util.Date;
import java.util.List;
import kz.kartayev.cinema.dto.CommentDto;
import kz.kartayev.cinema.model.Comment;
import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.service.CommentService;
import kz.kartayev.cinema.service.MovieService;
import kz.kartayev.cinema.service.PersonService;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for Movie.
 * */
@RestController
@RequestMapping("/movies")
public class MovieController {
  private final MovieService movieService;
  private final ModelMapper modelMapper;
  private final PersonService personService;
  private final CommentService commentService;

  /**
   * Movie Controller.
   * */
  @Autowired
  public MovieController(MovieService movieService, ModelMapper modelMapper,
                         PersonService personService, CommentService commentService) {
    this.movieService = movieService;
    this.modelMapper = modelMapper;
    this.personService = personService;
    this.commentService = commentService;
  }

  /**
   * Get description about movie.
   * */
  @GetMapping("/{id}")
  public Movie index(@PathVariable("id") int id) {
    return movieService.index(id);
  }

  /**
   * Get all comments in movie.
   * */
  @GetMapping("/{id}/comment")
  public List<Comment> comments(@PathVariable("id") int id) {
    return movieService.index(id).getComments();
  }

  /**
   * Save new comment.
   * */
  @PostMapping("/{id}/comment")
  public ResponseEntity<HttpStatus> saveComment(@PathVariable("id") int movieId, @RequestBody
                                                CommentDto commentDto) {
    // TODO: ADD BINDING RESULT AND VALIDATION FOR [0-5]
    Movie movie = movieService.index(movieId);
    Comment comment = toComment(commentDto);
    comment.setCreatedAt(new Date());
    comment.setPerson(personService.getInfo());
    comment.setMovie(movie);
    Hibernate.initialize(comment);
    double sum = movie.getRaiting();
    movie.setRaiting((sum + commentDto.getRating()) / (movie.getComments().size() + 1));
    Hibernate.initialize(movie);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Transfrom CommentDto to Comment class with Model mapper class.
   * */
  public Comment toComment(CommentDto commentDto) {
    return modelMapper.map(commentDto, Comment.class);
  }
}
