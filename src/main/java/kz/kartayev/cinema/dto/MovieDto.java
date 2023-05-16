package kz.kartayev.cinema.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
public class MovieDto {
  @NotNull
  private String name;
  @NotNull
  private String description;
  @NotNull
  private String genre;
  @NotNull
  private Date startDate;
  @NotNull
  private Date endDate;
  private double duration;

}
