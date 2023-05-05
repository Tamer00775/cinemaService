package kz.kartayev.cinema.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
// TODO : FIX ME!
@Data
public class TransactionDto {
  @Min(value = 1)
  @Max(value = 5)
  private int quantity;

}

