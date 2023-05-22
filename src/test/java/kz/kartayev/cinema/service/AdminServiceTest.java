package kz.kartayev.cinema.service;

import kz.kartayev.cinema.CinemaCenterApplication;
import kz.kartayev.cinema.model.Movie;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CinemaCenterApplication.class)
public class AdminServiceTest {
  @Autowired
  AdminService adminService;

  @Autowired
  MovieService movieService;

  @Test
  public void ratingShouldInRangeZeroAndFive(){
    List<Movie> movies = movieService.findAll().stream()
            .filter(a -> a.getRaiting() > 5 && a.getRaiting() < 0).collect(Collectors.toList());
    Assert.assertEquals(0, movies.size());
  }

  @Test
  public void dateShouldAfterAddTime(){
    Movie movie = new Movie();
    movie.setStartDate(new Date(2023, 1, 3));
    Assert.assertThrows("incorrect date", Exception.class,
            () -> adminService.saveMovie(movie, 1));
   }
}
