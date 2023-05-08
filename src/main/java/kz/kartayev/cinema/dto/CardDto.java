package kz.kartayev.cinema.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CardDto {
  @Size(min = 16, max = 16, message = "Your card must have 16 digits")
  @Pattern(regexp = "[0-9]{16}", message = "Your card must have only digits")
  private String card;
}
