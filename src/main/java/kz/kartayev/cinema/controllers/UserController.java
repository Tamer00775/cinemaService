package kz.kartayev.cinema.controllers;

import static kz.kartayev.cinema.util.ErrorUtil.getFieldErrors;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import kz.kartayev.cinema.dto.AuthenticationDTO;
import kz.kartayev.cinema.dto.CardDto;
import kz.kartayev.cinema.dto.MoneyDto;
import kz.kartayev.cinema.dto.PersonDto;
import kz.kartayev.cinema.model.Comment;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.model.Tickets;
import kz.kartayev.cinema.model.TransactionHistory;
import kz.kartayev.cinema.service.CommentService;
import kz.kartayev.cinema.service.PersonService;
import kz.kartayev.cinema.service.TicketService;
import kz.kartayev.cinema.service.TransactionService;
import kz.kartayev.cinema.util.ErrorMessage;
import kz.kartayev.cinema.util.ErrorResponse;
import kz.kartayev.cinema.util.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user in system.
 * */
@RestController
@RequestMapping("/user")
public class UserController {
  private final PersonService personService;
  private final ModelMapper modelMapper;
  private final TransactionService transactionService;
  private final UserValidator userValidator;
  private final CommentService commentService;
  private final TicketService ticketService;

  /**
   * User controller.
   * */
  @Autowired
  public UserController(PersonService personService,
                        ModelMapper modelMapper,
                        TransactionService transactionService,
                        UserValidator userValidator, CommentService commentService, TicketService ticketService) {
    this.personService = personService;
    this.modelMapper = modelMapper;
    this.transactionService = transactionService;
    this.userValidator = userValidator;
    this.commentService = commentService;
    this.ticketService = ticketService;
  }

  /**
   * Get user information.
   * */
  @GetMapping
  public Person getInfo() {
    return personService.getInfo();
  }

  /**
   * Get comments which written by user.
   * */
  @GetMapping("/mycomments")
  public List<Comment> myComments() {
     return commentService.findAll().stream()
             .filter(a -> a.getPerson().getUserId() == getInfo().getUserId())
             .collect(Collectors.toList());
  }

  /**
   * Set new money.
   * */
  @PostMapping("/money")
  public ResponseEntity<HttpStatus> addMoney(@RequestBody @Valid MoneyDto money,
                                             BindingResult bindingResult)  {
    userValidator.validate(personService.getInfo(), bindingResult);
    if (bindingResult.hasErrors()) {
      getFieldErrors(bindingResult);
    }
    Person person = personService.getInfo();
    person.setWallet(person.getWallet() + money.getTotalMoney());
    personService.save(person);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Delete card of user.
   * */
  @DeleteMapping("/card/delete")
  public ResponseEntity<HttpStatus> deleteMyCard() {
    Person person = personService.getInfo();
    person.setCard("");
    personService.save(person);
    return ResponseEntity.ok(HttpStatus.ACCEPTED);
  }

  @PostMapping("/password")
  public ResponseEntity<HttpStatus> changeMyPassword(@RequestBody AuthenticationDTO authenticationDTO){
    personService.changePassword(getInfo(), authenticationDTO.getPassword());
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Update your card.
   * */
  @PostMapping("/card/update")
  public ResponseEntity<HttpStatus> updateMyCard(@RequestBody @Valid CardDto cardDto,
                                                 BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      getFieldErrors(bindingResult);
    }
    Person person = personService.getInfo();
    person.setCard(cardDto.getCard());
    personService.save(person);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Get my transaction for buying ticket.
   * */
  @GetMapping("/transactions")
  public List<TransactionHistory> myTransactions() {
    return transactionService.getTicketsByPerson(personService.getInfo());
  }

  // TODO : FIX ME ADD FILTER
  /**
   * Get my tickets.
   * */
  @GetMapping("/tickets")
  public List<Tickets> myTickets() {
    return ticketService.findTicketsByPerson(personService.getInfo());
  }

  /**
   * Handler for exceptions.
   * */
  @ExceptionHandler
  public ResponseEntity<ErrorResponse> exceptionHandler(ErrorMessage errorMessage) {
    ErrorResponse errorResponse = new ErrorResponse(errorMessage.getMessage(),
            System.currentTimeMillis());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * Method for map to Dto.
   * */
  public PersonDto toPersonDto(Person person) {
    return modelMapper.map(person, PersonDto.class);
  }
}
