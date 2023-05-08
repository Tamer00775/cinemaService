package kz.kartayev.cinema.util;

import kz.kartayev.cinema.model.Comment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CommentValidator implements Validator {
  @Override
  public boolean supports(Class<?> clazz) {
    return false;
  }

  @Override
  public void validate(Object target, Errors errors) {
    Comment comment = (Comment) target;
    if(comment.getRating() < 0 && comment.getRating() > 5)
      errors.rejectValue("rating", "", "Rating of film should be "
              + "in range 0 and 5");
  }
}
