package kz.kartayev.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
