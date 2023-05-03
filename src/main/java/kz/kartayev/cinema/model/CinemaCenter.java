package kz.kartayev.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for cinema center.
 */
@Entity
@Table(name = "cinema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CinemaCenter {
  @Id
  @Column(name = "cinema_center_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int cinemaCenterId;
  @Column(name = "name")
  @NotNull
  private String name;

  @OneToMany(mappedBy = "cinemaCenter")
  @JsonIgnore
  private List<Movie> movieList;

}
