package kz.kartayev.cinema.util;

public class ErrorMessage extends RuntimeException{

  public ErrorMessage(String message){
    super(message);
  }
}
