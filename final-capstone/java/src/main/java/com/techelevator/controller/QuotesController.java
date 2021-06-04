package com.techelevator.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.techelevator.model.Quote;
import com.techelevator.services.QuotesService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class QuotesController {

    QuotesService quotesService = new QuotesService();

    @RequestMapping(path="/getQuotes", method = RequestMethod.GET)
    public List<Quote> quotesList() throws JsonProcessingException {
        return quotesService.getQuotesFromAPI();
    }

}
