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
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tickets")
@Entity
public class Tickets {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="ticket_id")
  int ticketId;
  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")
  private Movie movie;
  @ManyToOne
  @JsonIgnore
  @JoinColumn(name="seat_id", referencedColumnName = "seat_id")
  private Seats seats;
  @Column(name="created_at")
  private Date date;
  @ManyToOne
  @JsonIgnore
  @JoinColumn(name="person_id", referencedColumnName = "user_id")
  private Person person;
}
