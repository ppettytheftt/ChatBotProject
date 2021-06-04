package com.techelevator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Quote;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class QuotesService {

    public List<Quote> getQuotesFromAPI() throws JsonProcessingException {
        List<Quote> quotesList = new ArrayList<>();
        String url = "https://zenquotes.io/api/quotes";
        HttpEntity<String> httpEntity = new HttpEntity<>("");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,httpEntity,String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        for(int i = 0; i < 5; i++){
            String  q = jsonNode.path(i).path("q").asText();
            String a = jsonNode.path(i).path("a").asText();
            Quote quote = new Quote();
            quote.setQuote(q);
            quote.setAuthor(a);
            quotesList.add(quote);
        }
        return quotesList;
    }
}
