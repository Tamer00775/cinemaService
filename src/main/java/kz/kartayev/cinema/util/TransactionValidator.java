package kz.kartayev.cinema.util;

import kz.kartayev.cinema.model.TransactionHistory;
import kz.kartayev.cinema.service.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for check total price.
 * */
@Component
public class TransactionValidator implements Validator {
  private final PersonService personService;

  public TransactionValidator(PersonService personService) {
    this.personService = personService;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return false;
  }

  @Override
  public void validate(Object target, Errors errors) {
    TransactionHistory transactionHistory = (TransactionHistory) target;
    if(personService.getInfo().getWallet() < transactionHistory.getTotalPrice())
      errors.rejectValue("wallet", "", "You must have "
      + transactionHistory.getTotalPrice() + "KZT in your wallet. Please add some money"
              + " to your wallet, if you want buy ticket.");
  }
}
