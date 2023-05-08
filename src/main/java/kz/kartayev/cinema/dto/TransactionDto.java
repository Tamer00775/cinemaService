package kz.kartayev.cinema.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
// TODO : FIX ME!
@Data
public class TransactionDto {
  @Min(value = 1, message = "You must be buy at least one ticket")
  @Max(value = 5, message = "You must be buy most than 5 tickets")
  private int quantity;

}

