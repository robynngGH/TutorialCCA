package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.game.model.Game;
import jakarta.persistence.*;

import java.util.Date;

/**
 * @author rnavarro
 *
 */
@Entity
@Table(name = "loan")
public class Loan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "game_id", nullable = false)
  private Game game;

  @ManyToOne
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @Column(name = "start_date", nullable = false)
  private Date start_date;

  @Column(name = "end_date", nullable = false)
  private Date end_date;

  /**
   *
   * @return id
   */
  public Long getId() {
    return id;
  }

  /**
   *
   * @param id new value of {@link #getId}
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   *
   * @return game
   */
  public Game getGame() {
    return game;
  }

  /**
   *
   * @param game new value of {@link #getGame}
   */
  public void setGame(Game game) {
    this.game = game;
  }

  /**
   *
   * @return customer
   */
  public Customer getCustomer() {
    return customer;
  }

  /**
   *
   * @param customer new value of {@link #getCustomer}
   */
  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  /**
   *
   * @return start_date
   */
  public Date getStart_date() {
    return start_date;
  }

  /**
   *
   * @param start_date new value of {@link #getStart_date}
   */
  public void setStart_date(Date start_date) {
    this.start_date = start_date;
  }

  /**
   *
   * @return end_date
   */
  public Date getEnd_date() {
    return end_date;
  }

  /**
   *
   * @param end_date new value of {@link #getEnd_date}
   */
  public void setEnd_date(Date end_date) {
    this.end_date = end_date;
  }
}
