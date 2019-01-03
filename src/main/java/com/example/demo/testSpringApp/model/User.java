package com.example.demo.testSpringApp.model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "users")

public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @OneToMany (mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Quote> quotes;

    public User() {
    }

    public User(String name) {
        this.name = name;
        quotes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) &&
                Objects.equals(quotes, user.quotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quotes);
    }

    @Override
    public String toString() {
        return name;
    }
}
