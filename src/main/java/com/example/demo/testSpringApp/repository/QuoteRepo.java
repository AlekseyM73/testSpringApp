package com.example.demo.testSpringApp.repository;
import com.example.demo.testSpringApp.model.Quote;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepo extends PagingAndSortingRepository<Quote, Integer> {

}
