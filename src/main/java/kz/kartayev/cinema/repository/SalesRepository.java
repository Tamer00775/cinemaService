package kz.kartayev.cinema.repository;

import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {
}
