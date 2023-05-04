package kz.kartayev.cinema.service;

import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.model.TransactionHistory;
import kz.kartayev.cinema.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final PersonService personService;
  @Autowired
  public TransactionService(TransactionRepository transactionRepository, PersonService personService) {
    this.transactionRepository = transactionRepository;
    this.personService = personService;
  }
  @Transactional
  public void buyTicket(int movie_id, TransactionHistory history){
    history.setCreatedAt(new Date());
    Person person = personService.getInfo();
    person.setWallet(person.getWallet() - history.getTotalPrice());
    history.setPerson(person);
    transactionRepository.save(history);
  }
}
