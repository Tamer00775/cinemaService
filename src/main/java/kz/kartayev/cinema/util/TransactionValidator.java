package kz.kartayev.cinema.util;

import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.model.Tickets;
import kz.kartayev.cinema.model.TransactionHistory;
import kz.kartayev.cinema.service.SeatsService;
import kz.kartayev.cinema.service.TicketService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

/**
 * Validator for check total price.
 * */
@Component
public class TransactionValidator implements Validator {
  private final TicketService ticketService;
  public TransactionValidator(SeatsService seatsService, TicketService ticketService) {

    this.ticketService = ticketService;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return false;
  }
 // TODO : FIX ME
  @Override
  public void validate(Object target, Errors errors) {
    TransactionHistory transactionHistory = (TransactionHistory) target;
    Movie movie = transactionHistory.getMovie();
    int[] places = transactionHistory.getReserved();
    for(int i : places) {
      if(i <= 0 || i > 17) {
        errors.rejectValue("reserved", "", "You must be choose in 0 and 17 range!");
      }
      Optional<Tickets> tickets = ticketService.findByTicketIdAndMovieId(i, movie);
      if(tickets.isPresent()) {
        errors.rejectValue("reserved", "", "This place already reserved! Please "
                + "choose another places!");
      }
    }
  }
}
