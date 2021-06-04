package com.techelevator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Quote;
import com.techelevator.model.Weather;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeatherService {
    public Weather getWeatherFromAPI(int postCode) throws JsonProcessingException {
        Weather weather = new Weather();
        String baseUrl = "http://api.weatherapi.com/v1/current.json?key=";
        //Weather API Key: API Key: 92a78deba4e74964a27144611213005
        String apiKey = "92a78deba4e74964a27144611213005";
        String zipCodeQuery = "&q=";
        int zipCode = postCode;
        String remainder = "&aqi=no";
        String completeApiUrl = baseUrl + apiKey + zipCodeQuery + zipCode + remainder;
        HttpEntity<String> httpEntity = new HttpEntity<>("");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(completeApiUrl, HttpMethod.GET,httpEntity,String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        String nameOfLocation = jsonNode.path("location").path("name").asText();
        String stateLocated = jsonNode.path("location").path("region").asText();
        String dateTime = jsonNode.path("location").path("localtime").asText();
        //String dateTime = jsonNode.path("current").path("last_updated").asText();
        String currentTemperature = jsonNode.path("current").path("temp_f").asText();
       // String str = "2016-03-04 11:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm", Locale.US);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

        weather.setNameOfLocation(nameOfLocation);
        weather.setStateLocated(stateLocated);
        weather.setLocalDateAndTime(localDateTime);
        weather.setCurrentTemperature(currentTemperature);
        return weather;
    }
}
