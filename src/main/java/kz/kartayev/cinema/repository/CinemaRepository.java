package kz.kartayev.cinema.repository;

import kz.kartayev.cinema.model.CinemaCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Cinema class.
 * */
@Repository
public interface CinemaRepository extends JpaRepository<CinemaCenter, Integer> {

}
