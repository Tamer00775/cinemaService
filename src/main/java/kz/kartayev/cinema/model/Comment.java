package kz.kartayev.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * entity for commenting.
 * */
@Entity
@Table(name="comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
  @Id
  @Column(name= "comment_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int commentId;
  @Column(name="comment")
  @NotNull(message = "comments")
  private String comments;
  @Column(name="rating")
  @NotNull(message = "ratings")
  private int rating;
  @Column(name="created_at")
  @NotNull(message = "createdAt")
  private Date createdAt;
  @ManyToOne
  @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")
  @JsonIgnore
  private Movie movie;
  @ManyToOne
  @JoinColumn(name="user_id", referencedColumnName = "user_id")
  @JsonIgnore
  private Person person;
}
