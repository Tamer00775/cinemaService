package kz.kartayev.cinema.util;

import kz.kartayev.cinema.model.Movie;
import kz.kartayev.cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieValidator implements Validator {
  private final MovieService movieService;
  @Autowired
  public MovieValidator(MovieService movieService) {
    this.movieService = movieService;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return false;
  }

  @Override
  public void validate(Object target, Errors errors) {
    Movie movie = (Movie) target;
    Date checkDate = movie.getStartDate();
    List<Movie> movieList = movieService.findAll().stream()
            .filter(a -> checkDate.after(a.getStartDate()) &&
                    checkDate.before(a.getEndDate())).collect(Collectors.toList());
    if(!movieList.isEmpty()) {
      errors.rejectValue("startDate", "","You have conflict date's! In this date already set movie!");
    }
  }
}
