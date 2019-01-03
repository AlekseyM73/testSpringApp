package com.example.demo.testSpringApp.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "quotes")

public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;
    @Column (name = "text", length = 65535)
    private String text;
    @Column (name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public Quote() {
    }

    public Quote(String text, Date date, User user) {
        this.text = text;
        this.date = date;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getDate() {

        return convertDate(date);
    }

    public User getUser() {
        return user;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return date == quote.date &&
                Objects.equals(text, quote.text) &&
                Objects.equals(user, quote.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, date, user);
    }

    @Override
    public String toString() {
        return text;
    }

    private String convertDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        if (Integer.valueOf(month) < 10){
            month = "0"+month;
        }
        if (Integer.valueOf(day) < 10){
            day = "0"+day;
        }
        String convertDate = day + "/" + month + "/" + year;

        return convertDate;
    }
}
