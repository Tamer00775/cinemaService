package kz.kartayev.cinema.repository;

import kz.kartayev.cinema.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for movie class.
 * */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
