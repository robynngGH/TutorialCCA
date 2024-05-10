package com.ccsw.tutorial.category.model;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement in MySQL or SQLServer
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name new value of {@link #getName}
     */
    public void setName(String name) {
        this.name = name;
    }

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
}
