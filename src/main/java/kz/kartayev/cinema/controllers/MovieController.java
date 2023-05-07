package kz.kartayev.cinema.controllers;

import java.util.List;
import kz.kartayev.cinema.dto.CommentDto;
import kz.kartayev.cinema.dto.TransactionDto;
import kz.kartayev.cinema.model.Comment;
import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.model.TransactionHistory;
import kz.kartayev.cinema.service.CommentService;
import kz.kartayev.cinema.service.MovieService;
import kz.kartayev.cinema.service.PersonService;
import kz.kartayev.cinema.service.TransactionService;
import org.hibernate.Hibernate;
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

import javax.validation.Valid;

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
  private final TransactionService transactionService;

  /**
   * Movie Controller.
   * */
  @Autowired
  public MovieController(MovieService movieService, ModelMapper modelMapper,
                         PersonService personService, CommentService commentService, TransactionService transactionService) {
    this.movieService = movieService;
    this.modelMapper = modelMapper;
    this.personService = personService;
    this.commentService = commentService;
    this.transactionService = transactionService;
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
  // TODO: FIX METHOD FOR DELETE COMMENT
  @DeleteMapping("/{id}/comment/{comment_id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") int movieId,
                                                  @PathVariable("comment_id") int commentId){
    movieService.deleteComment(movieId, commentId);
    return ResponseEntity.ok(HttpStatus.OK);
  }


  /**
   * Save new comment.
   * */
  @PostMapping("/{id}/comment")
  public ResponseEntity<HttpStatus> saveComment(@PathVariable("id") int movieId, @RequestBody
                                                CommentDto commentDto) {
    // TODO: ADD BINDING RESULT AND VALIDATION FOR [0-5] оценка
    movieService.save(movieId, toComment(commentDto)) ;
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @PostMapping("/{id}/buy")
  public ResponseEntity<HttpStatus> buyTickets(@PathVariable("id") int movie_id, @RequestBody
                                               @Valid TransactionDto transactionDto){
    TransactionHistory transactionHistory = toTransaction(transactionDto);
    transactionHistory.setMovie(index(movie_id));
    transactionHistory.setCinemaCenter(index(movie_id).getCinemaCenter());
    transactionHistory.setTotalPrice(transactionDto.getQuantity() * index(movie_id).getPrice());
    transactionService.buyTicket(transactionHistory);
    Movie movie = index(movie_id);
    movie.setPlaces(movie.getPlaces() - transactionDto.getQuantity());
    movieService.save(movie);
    Hibernate.initialize(movie);
    Hibernate.initialize(transactionHistory);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Transfrom CommentDto to Comment class with Model mapper class.
   * */
  public Comment toComment(CommentDto commentDto) {
    return modelMapper.map(commentDto, Comment.class);
  }

  public TransactionHistory toTransaction(TransactionDto transactionDto){
    return modelMapper.map(transactionDto, TransactionHistory.class);
  }
}
