package kz.kartayev.cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
public class MovieDto {
  @NotNull(message = "name")
  private String name;
  @NotNull(message = "decription")
  private String description;
  @NotNull(message = "genre")
  private String genre;
  @NotNull(message = "start date")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm dd-MM-yyyy")
  private Date startDate;
  @NotNull(message = "end date")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm dd-MM-yyyy")
  private Date endDate;
  private double duration;

}
