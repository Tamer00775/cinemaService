package kz.kartayev.cinema.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data transfer object for Comment class.
 * */
@Data
public class CommentDto {
  @NotNull
  private int rating;
  @NotNull
  private String comments;
}
