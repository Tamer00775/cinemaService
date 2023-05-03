package kz.kartayev.cinema.dto;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * Person data transfer object.
 */
@Data
public class PersonDto {
  @Size(min = 2, max = 30, message = "Your name must be in range 2 and 30 characters")
  @NotEmpty
  private String username;

  @Min(value = 18, message = "You must under 18 age")
  @NotNull(message = "Your age should be not empty")
  private int age;

  @Column(name = "card")
  @Size(min = 16, max = 16)
  private String card;

  @Column(name = "password")
  @Size(min = 4, max = 14, message = "Your password should be in range 4 and 14 characters")
  // @Pattern(regexp = "[A-Z]+[a-z]+[0-9]+")
  private String password;
}
