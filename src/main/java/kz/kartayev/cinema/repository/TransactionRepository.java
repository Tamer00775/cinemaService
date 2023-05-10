package kz.kartayev.cinema.repository;

import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionHistory, Integer> {
  List<TransactionHistory> findByPerson(Person person);
}
