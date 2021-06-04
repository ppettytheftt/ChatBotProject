package com.techelevator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.JobSearch;
import com.techelevator.model.Weather;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JobSearchService {


    public List<JobSearch> getOpenJobs() throws JsonProcessingException {
        List<JobSearch> openJobsList = new ArrayList<>();
        //https://www.themuse.com/api/public/jobs?category=IT&category=Software%20Engineer&category=UX&level=Entry%20Level&level=Mid%20Level&location=New%20York%2C%20NY&page=1
        //https://www.themuse.com/api/public/jobs?category=Software%20Engineer&level=Mid%20Level&location=Columbus&page=1
        //https://www.themuse.com/api/public/jobs?category=IT&category=Software%20Engineer&category=UX&level=Entry%20Level&level=Mid%20Level&location=Dallas%2C%20TX&page=1
        //https://www.themuse.com/api/public/jobs?category=IT&category=Software%20Engineer&category=UX&level=Entry%20Level&level=Mid%20Level&location=Chicago%2C%20IL&page=1


     /*   String baseUrl = "https://www.themuse.com/api/public/jobs?category=IT&category=Software%20Engineer&category=UX&level=Entry%20Level&level=Mid%20Level&location=";
        String remainder = "%2C%20NY&page=1";
        String cityToSearch = city;*/
        String baseUrl = "https://www.themuse.com/api/public/jobs?category=IT&category=Software%20Engineer&category=UX&level=Entry%20Level&level=Mid%20Level&location=New%20York%2C%20NY&page=1";

        String completeApiUrl = baseUrl;
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "PostmanRuntime/7.26.8");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(completeApiUrl, HttpMethod.GET, httpEntity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        for(int i =0; i < 5; i++){
            String locationLink = jsonNode.path("results").path(i).path("refs").path("landing_page").asText();
            JobSearch job = new JobSearch();
            job.setLandingPageLink(locationLink);
            openJobsList.add(job);
        }
        return openJobsList;
    }
}
