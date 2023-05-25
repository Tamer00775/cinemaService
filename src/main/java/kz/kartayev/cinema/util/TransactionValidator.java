package kz.kartayev.cinema.util;

import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.model.TransactionHistory;
import kz.kartayev.cinema.service.PersonService;
import kz.kartayev.cinema.service.SeatsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for check total price.
 * */
@Component
public class TransactionValidator implements Validator {
  private final SeatsService seatsService;

  public TransactionValidator(SeatsService seatsService) {
    this.seatsService = seatsService;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return false;
  }
 // TODO : FIX ME
  @Override
  public void validate(Object target, Errors errors) {


  }
}
