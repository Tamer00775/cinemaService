package kz.kartayev.cinema.repository;

import kz.kartayev.cinema.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for movie class.
 * */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
  Movie findByName(String name);

  List<Movie> findByNameStartingWith(String prefix);
}
