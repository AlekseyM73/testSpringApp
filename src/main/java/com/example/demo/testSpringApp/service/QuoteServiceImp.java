package com.example.demo.testSpringApp.service;
import com.example.demo.testSpringApp.model.Quote;
import com.example.demo.testSpringApp.repository.QuoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteServiceImp implements QuoteService {

    @Autowired
    QuoteRepo quoteRepo;

    @Override
    public Iterable<Quote> getAll() {
        return quoteRepo.findAll();
    }

    @Override
    public void addQuote(Quote quote) {
        quoteRepo.save(quote);
    }
}
