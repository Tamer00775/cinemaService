package kz.kartayev.cinema.model;

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
import java.util.Date;

@Entity
@Table(name="transaction_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "transaction_id")
  private int transactionId;
  @Column(name="total_price")
  private int totalPrice;
  @Column(name="created_at")
  private Date createdAt;
  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private Person person;
  @ManyToOne
  @JoinColumn(name="movie_id", referencedColumnName = "movie_id")
  private Movie movie;
  @ManyToOne
  @JoinColumn(name="cinema_id", referencedColumnName = "cinema_center_id")
  private CinemaCenter cinemaCenter;

}