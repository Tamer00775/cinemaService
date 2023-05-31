package kz.kartayev.cinema.service;

import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.model.Tickets;
import kz.kartayev.cinema.repository.SeatsRepository;
import kz.kartayev.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
  private final TicketRepository ticketRepository;
  private final SeatsRepository seatsRepository;
  @Autowired
  public TicketService(TicketRepository ticketRepository, SeatsRepository seatsRepository) {
    this.ticketRepository = ticketRepository;
    this.seatsRepository = seatsRepository;
  }
  @Transactional
  public void save(Tickets ticket){
    ticketRepository.save(ticket);
  }

  public List<Tickets> findTicketsByPerson(Person person) {
    return ticketRepository.findByPerson(person);
  }
  public Optional<Tickets> findBySeatsAndMovieId(int id, Movie movie) {
    return ticketRepository.findBySeatsAndMovie(seatsRepository.findById(id).get(), movie);
  }
}
