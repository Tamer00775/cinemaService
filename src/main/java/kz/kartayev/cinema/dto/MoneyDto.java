package kz.kartayev.cinema.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class MoneyDto {
  @Min(value = 1000, message = "You must be set more than 1000 kzt")
  private int totalMoney;
}
