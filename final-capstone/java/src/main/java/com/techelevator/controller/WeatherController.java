package com.techelevator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techelevator.model.Weather;
import com.techelevator.services.WeatherService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class WeatherController {
    WeatherService weather = new WeatherService();

    @RequestMapping(path="/weather/{zipCode}", method = RequestMethod.GET)
    public Weather getWeather(@PathVariable int zipCode) throws JsonProcessingException {
    return weather.getWeatherFromAPI(zipCode);
    }
}
