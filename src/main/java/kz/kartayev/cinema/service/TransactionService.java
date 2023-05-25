package kz.kartayev.cinema.service;

import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.model.Seats;
import kz.kartayev.cinema.model.Tickets;
import kz.kartayev.cinema.model.TransactionHistory;
import kz.kartayev.cinema.repository.TransactionRepository;
import kz.kartayev.cinema.util.ErrorMessage;
import kz.kartayev.cinema.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final PersonService personService;
  private final MailSender mailSender;
  private final MovieService movieService;
  private final SeatsService seatsService;
  private final TicketService ticketService;
  @Autowired
  public TransactionService(TransactionRepository transactionRepository,
                            PersonService personService,
                            MailSender mailSender, MovieService movieService, SeatsService seatsService, TicketService ticketService) {
    this.transactionRepository = transactionRepository;
    this.personService = personService;
    this.mailSender = mailSender;
    this.movieService = movieService;
    this.seatsService = seatsService;
    this.ticketService = ticketService;
  }
  public List<TransactionHistory> findAll(Person person){
    return transactionRepository.findByPerson(person);
  }
   // TODO : Add validation for buying movie which already finished.
  @Transactional
  public void buyTicket(Movie movie, int[] reserve, TransactionHistory history){
    Person person = personService.getInfo();
    System.out.println("ARRAY: " + Arrays.toString(reserve));
    history.setCreatedAt(new Date());
    history.setCinemaCenter(movie.getCinemaCenter());
    history.setPerson(person);
    int totalPrice = 0;

    for(int i : reserve) {
      Tickets tickets = new Tickets() ;
      tickets.setMovie(movie);
      Seats seats = seatsService.findById(i);
      totalPrice += seats.getPrice();
      tickets.setSeats(seats);
      tickets.setDate(new Date());
      tickets.setPerson(person);
      ticketService.save(tickets);
    }

    history.setTotalPrice(totalPrice); // FIX
    movie.setPlaces(movie.getPlaces() - reserve.length);

    movieService.save(movie);
    transactionRepository.save(history);

    person.setWallet(person.getWallet() - history.getTotalPrice());
    personService.save(person);

    String message = "Hello! You have " + Arrays.toString(reserve) + " ticket's for "
            + movie.getName() + " at " + movie.getStartDate() + ". Good luck!";
    mailSender.send(person.getUsername(), "Tamerlan Cinema Center", message);
  }

  public List<TransactionHistory> getTicketsByPerson(Person person){
    // return transactionRepository.getTransactionHistoriesByPerson(person.getUsername());
    return transactionRepository.getTransactionHistoriesByPerson(person);
  }
  /**
   * Handler for exceptions.
   */
  @ExceptionHandler
  public ResponseEntity<ErrorResponse> exceptionHandler(ErrorMessage errorMessage) {
    ErrorResponse errorResponse = new ErrorResponse(errorMessage.getMessage(),
            System.currentTimeMillis());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
