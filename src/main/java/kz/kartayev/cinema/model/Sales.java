package kz.kartayev.cinema.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sales {
  @Id
  @Column(name="sale_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int saleId;
  @ManyToOne
  @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")
  @JsonIgnore
  private Movie movie;

}
