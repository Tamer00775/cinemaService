package kz.kartayev.cinema.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class TransactionDto {
  private int movieId;
  private int cinemaId;
  @Min(value = 1)
  @Max(value = 5)
  private int quantity;

}

