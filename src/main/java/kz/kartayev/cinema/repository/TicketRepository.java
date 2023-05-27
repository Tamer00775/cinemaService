package kz.kartayev.cinema.repository;

import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.model.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, Integer> {
  List<Tickets> findByPerson(Person person);
  Optional<Tickets> findByTicketIdAndMovie(int id, Movie movie);
}
