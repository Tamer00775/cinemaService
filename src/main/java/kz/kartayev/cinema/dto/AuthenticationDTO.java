package kz.kartayev.cinema.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class AuthenticationDTO {
  @Size(min = 2, max = 30, message = "Your name must be in range 2 and 30 characters")
  @NotEmpty
  @Pattern(regexp = "^[A-z]*[0-9]*@(gmail|yandex|mail)\\.(com|ru)$", message = "Ex: some@gmail.com")
  private String username;

  @Column(name = "password")
  @Size(min = 4, max = 14, message = "Your password should be in range 4 and 14 characters")
  // @Pattern(regexp = "[A-Z]+[a-z]+[0-9]+")
  private String password;
}
