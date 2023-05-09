package kz.kartayev.cinema.service;

import kz.kartayev.cinema.CinemaCenterApplication;
import kz.kartayev.cinema.util.ErrorMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CinemaCenterApplication.class)
public class PersonServiceTest {
  @Autowired
  MovieService movieService;
  @Test
  public void getMovieByMovieName(){
    String movieName = "don't tell me anything zhai gana bile";
    MovieService movieServiceMock = Mockito.mock(MovieService.class);
    Mockito.when(movieServiceMock.getMovieByName(movieName)).thenThrow(ErrorMessage.class);
    Assert.assertThrows("Long movie name", ErrorMessage.class,
            () -> movieServiceMock.getMovieByName(movieName));
  }

}
