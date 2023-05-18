package kz.kartayev.cinema.repository;

import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionHistory, Integer> {
  List<TransactionHistory> findByPerson(Person person);

  @Query(value = "SELECT * FROM transaction_history LEFT JOIN person on transaction_history.user_id = person.user_id "
          + "AND person.username = :username", nativeQuery = true)
  List<TransactionHistory> getTransactionHistoriesByPerson(@Param("username") String username);

  List<TransactionHistory> getTransactionHistoriesByPerson(Person person);
}
