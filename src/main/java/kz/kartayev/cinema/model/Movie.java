package kz.kartayev.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for Movie entity.
 * */
@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
  @Id
  @Column(name = "movie_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int movieId;
  @Column(name = "movie_name")
  @NotNull
  private String name;
  @Column(name = "description")
  @NotNull
  private String description;
  @Column(name = "genre")
  @NotNull
  private String genre;
  @Column(name = "inwhattimestart")
  @NotNull
  private Date startDate;
  @Column(name = "inwhattimeend")
  @NotNull
  private Date endDate;
  @Column(name = "availableplace")
  private int places;
  @Column(name = "price")
  private int price;
  @Column(name = "raiting")
  private double raiting;
  @Column(name = "duration")
  private double duration;
  @ManyToOne
  @JoinColumn(name = "cinema_center_id", referencedColumnName = "cinema_center_id")
  @JsonIgnore
  private CinemaCenter cinemaCenter;
  @OneToMany(mappedBy = "movie")
  @JsonIgnore
  List<Comment> comments;
}
