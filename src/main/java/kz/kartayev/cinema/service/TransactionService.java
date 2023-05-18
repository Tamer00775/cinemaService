package kz.kartayev.cinema.service;

import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.model.TransactionHistory;
import kz.kartayev.cinema.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final PersonService personService;
  private final MailSender mailSender;
  @Autowired
  public TransactionService(TransactionRepository transactionRepository, PersonService personService, MailSender mailSender) {
    this.transactionRepository = transactionRepository;
    this.personService = personService;
    this.mailSender = mailSender;
  }
  public List<TransactionHistory> findAll(Person person){
    return transactionRepository.findByPerson(person);
  }
  @Transactional
  public void buyTicket(TransactionHistory history, Movie movie, int quantity){
    history.setCreatedAt(new Date());
    Person person = personService.getInfo();
    history.setMovie(movie);
    history.setCinemaCenter(movie.getCinemaCenter());
    history.setTotalPrice(quantity * movie.getPrice());
    person.setWallet(person.getWallet() - history.getTotalPrice());
    personService.save(person);
    history.setPerson(person);
    transactionRepository.save(history);
    String message = "Hello! You have " + quantity + " ticket for "
            + movie.getName() + " at " + movie.getStartDate() + ". Good luck!";
    mailSender.send(person.getUsername(), "ticket", message);
  }

  public List<TransactionHistory> getTicketsByPerson(Person person){
    // return transactionRepository.getTransactionHistoriesByPerson(person.getUsername());
    return transactionRepository.getTransactionHistoriesByPerson(person);
  }
}
