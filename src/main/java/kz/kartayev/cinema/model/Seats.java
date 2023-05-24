package kz.kartayev.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="seats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seats {
  @Id
  @Column(name="seat_id")
  private int seatId;
  @Column(name="price")
  private int price;

  @OneToOne
  @JsonIgnore
  @Transient
  private Movie movie;

  @Column(name="reserved")
  private boolean reserved;
}
