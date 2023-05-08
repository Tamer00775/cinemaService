package kz.kartayev.cinema.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorUtil {

  public static void getFieldErrors(BindingResult bindingResult){
    StringBuilder errors = new StringBuilder();
    List<FieldError> err = bindingResult.getFieldErrors();
    for(FieldError error : err){
      errors.append(error.getField())
              .append(" - ").append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
              .append(";");

    }
    throw new ErrorMessage(errors.toString());
  }
}
