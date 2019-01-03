package com.example.demo.testSpringApp.service;
import com.example.demo.testSpringApp.model.Quote;

public interface QuoteService {

    Iterable<Quote> getAll();
    void addQuote (Quote quote);

}
