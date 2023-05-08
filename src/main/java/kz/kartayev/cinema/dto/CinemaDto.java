package kz.kartayev.cinema.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CinemaDto {
  @NotNull(message = "You should write name for Cinema Center!")
  @Size(min = 3, max = 15, message = "Name of Cinema Center must be in range 3 - 15 characters")
  private String name;
}
