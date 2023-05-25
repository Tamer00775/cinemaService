package kz.kartayev.cinema.repository;

import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.model.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, Integer> {
  List<Tickets> findByPerson(Person person);
}
