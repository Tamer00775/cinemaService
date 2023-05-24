package kz.kartayev.cinema.dto;

import lombok.Data;

import javax.validation.constraints.Size;

// TODO : FIX ME!
@Data
public class TransactionDto {
  @Size(min = 1, max = 5, message = "You should buy at least one ticket and at most 5")
  private int[] reserved;
}

