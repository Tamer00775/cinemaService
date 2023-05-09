package kz.kartayev.cinema.controllers;

import java.util.List;
import java.util.stream.Collectors;

import kz.kartayev.cinema.dto.CardDto;
import kz.kartayev.cinema.dto.MoneyDto;
import kz.kartayev.cinema.dto.PersonDto;
import kz.kartayev.cinema.model.Comment;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.model.TransactionHistory;
import kz.kartayev.cinema.service.CommentService;
import kz.kartayev.cinema.service.PersonService;
import kz.kartayev.cinema.service.TransactionService;
import kz.kartayev.cinema.util.ErrorMessage;
import kz.kartayev.cinema.util.ErrorResponse;
import kz.kartayev.cinema.util.UserValidator;
import org.hibernate.Hibernate;
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

import javax.validation.Valid;
import static kz.kartayev.cinema.util.ErrorUtil.getFieldErrors;


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

  @Autowired
  public UserController(PersonService personService, ModelMapper modelMapper, TransactionService transactionService, UserValidator userValidator, CommentService commentService) {
    this.personService = personService;
    this.modelMapper = modelMapper;
    this.transactionService = transactionService;
    this.userValidator = userValidator;
    this.commentService = commentService;
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
     return commentService.findAll().stream().filter(a -> a.getPerson().getUserId() == getInfo().getUserId())
            .collect(Collectors.toList());
  }
  /**
   * Set new money.
   * */
  @PostMapping("/money")
  public ResponseEntity<HttpStatus> addMoney(@RequestBody @Valid MoneyDto money,
                                             BindingResult bindingResult){
    userValidator.validate(personService.getInfo(), bindingResult);
    if(bindingResult.hasErrors())
      getFieldErrors(bindingResult);

    Person person = personService.getInfo();
    person.setWallet(person.getWallet() + money.getTotalMoney());
    personService.save(person);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Delete card of user.
   * */
  @DeleteMapping("/card/delete")
  public ResponseEntity<HttpStatus> deleteMyCard(){
    Person person = personService.getInfo();
    person.setCard("");
    personService.save(person);
    return ResponseEntity.ok(HttpStatus.ACCEPTED);
  }

  /**
   * Update your card.
   * */
  @PostMapping("/card/update")
  public ResponseEntity<HttpStatus> updateMyCard(@RequestBody @Valid CardDto cardDto,
                                                 BindingResult bindingResult){
    if(bindingResult.hasErrors())
      getFieldErrors(bindingResult);
    Person person = personService.getInfo();
    person.setCard(cardDto.getCard());
    personService.save(person);
    return ResponseEntity.ok(HttpStatus.OK);
  }
  //TODO:  FIX ME
  @GetMapping("/tickets")
  public List<TransactionHistory> myTickets(){
    Person person = personService.getInfo();
    return transactionService.findAll()
            .stream().filter(a -> a.getPerson().getUserId() == person.getUserId())
            .collect(Collectors.toList());
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
