package kz.kartayev.cinema.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Model class for User in application.
 * */
@Entity
@Table(name = "person")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private int userId;

  @Column(name = "username")
  @Size(min = 2, max = 30, message = "Your name must be in range 2 and 30 characters")
  @NotEmpty
  private String username;

  @Column(name = "age")
  @Min(value = 18, message = "You must under 18 age")
  @NotNull(message = "Your age should be not empty")
  private int age;

  @Column(name = "card")
  @Size(min = 16, max = 16)
  private String card;

  @Column(name = "role")
  @NotEmpty(message = "role should be not empty")
  private String role;

  @Column(name = "password")
  private String password;

  @OneToMany(mappedBy = "person")
  @JsonIgnore
  private List<Comment> comments;
}
