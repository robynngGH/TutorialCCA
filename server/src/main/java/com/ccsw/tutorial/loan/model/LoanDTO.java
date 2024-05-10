package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.customer.model.CustomerDTO;
import com.ccsw.tutorial.game.model.GameDTO;

import java.util.Date;

/**
 * @author rnavarro
 *
 */
public class LoanDTO {

    private Long id;
    private GameDTO game;
    private CustomerDTO customer;
    private Date start_date;
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
    public GameDTO getGame() {
        return game;
    }

    /**
     *
     * @param game new value of {@link #getGame}
     */
    public void setGame(GameDTO game) {
        this.game = game;
    }

    /**
     *
     * @return customer
     */
    public CustomerDTO getCustomer() {
        return customer;
    }

    /**
     *
     * @param customer new value of {@link #getCustomer}
     */
    public void setCustomer(CustomerDTO customer) {
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
