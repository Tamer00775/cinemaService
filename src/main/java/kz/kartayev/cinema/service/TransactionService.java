package kz.kartayev.cinema.service;

import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.model.TransactionHistory;
import kz.kartayev.cinema.repository.TransactionRepository;
import kz.kartayev.cinema.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final PersonService personService;
  @Autowired
  public TransactionService(TransactionRepository transactionRepository, PersonService personService) {
    this.transactionRepository = transactionRepository;
    this.personService = personService;
  }
  public List<TransactionHistory> findAll(){
    return transactionRepository.findAll();
  }
  // TODO:Why transaction is not working?
  @Transactional
  public void buyTicket(TransactionHistory history, Movie movie, int quantity){
    history.setCreatedAt(new Date());
    Person person = personService.getInfo();
    history.setMovie(movie);
    history.setCinemaCenter(movie.getCinemaCenter());
    history.setTotalPrice(quantity * movie.getPrice());
   // if(person.getWallet() < history.getTotalPrice())
   //   throw new ErrorMessage("You dont have money to pay this transaction");
   // if(person.getCard().isEmpty())
   //   throw new ErrorMessage("You dont have card. Add card!");
    person.setWallet(person.getWallet() - history.getTotalPrice());
    personService.save(person);
    history.setPerson(person);
    transactionRepository.save(history);
  }
}
