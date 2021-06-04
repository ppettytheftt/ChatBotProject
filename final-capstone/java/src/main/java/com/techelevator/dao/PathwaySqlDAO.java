package com.techelevator.dao;

import com.techelevator.model.RegisterUserDTO;
import com.techelevator.model.Response;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Component
public class PathwaySqlDAO implements PathwayDAO {

    JdbcTemplate jdbcTemplate;

    public PathwaySqlDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Response getPathwayDetails(String topic) {
        Response response = new Response();
        if (topic.equalsIgnoreCase("technical")) {
            response = fetchTechnicalResults();
            return response;
        } else if (topic.equalsIgnoreCase("cover")) {
            response =fetchCoverResults();
            return response;

        } else if (topic.equalsIgnoreCase("attire")) {
            response = fetchAttireResults();
                return response;

        } else if (topic.equalsIgnoreCase("behavioral")) {
            response = fetchBehavioralResults();
            return response;
        }

         else if (topic.equalsIgnoreCase("general")) {
             response = fetchGeneralResults();
             return response;
        }
         return null;
    }

    public Response fetchTechnicalResults(){
        Response response = new Response();
        List<String> responseList = new ArrayList<>();
        List<String> responseLinkList = new ArrayList<>();
        String sql = "SELECT response, responselinks FROM technical_questions;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            String responseText = mapToResponse(results);
            responseList.add(responseText);
            if(!(results.getString("responselinks") == null)){
                String responseLink = mapToResponseLink(results);
                responseLinkList.add(responseLink);
            }
        }
        response.setResponseTextList(responseList);
        response.setResponseLinkList(responseLinkList);
        return response;
    }

    public Response fetchBehavioralResults(){
        Response behavioralResponse = new Response();
        List<String> responseList = new ArrayList<>();
        List<String> responseLinkList = new ArrayList<>();
        String sql = "SELECT response, responselinks FROM behavioral_questions;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            String responseText = mapToResponse(results);
            responseList.add(responseText);
            if(!(results.getString("responselinks") == null)){
                String responseLink = mapToResponseLink(results);
                responseLinkList.add(responseLink);
            }
        }
        behavioralResponse.setResponseTextList(responseList);
        behavioralResponse.setResponseLinkList(responseLinkList);
        return behavioralResponse;
    }

    public Response fetchCoverResults() {
        Response coverResponse = new Response();
        List<String> responseLinkList = new ArrayList<>();
        String sql = "SELECT response FROM cover_letter ;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            String responseLink = results.getString("response");
            responseLinkList.add(responseLink);

        }
        coverResponse.setResponseLinkList(responseLinkList);
        return coverResponse;
    }
    public Response fetchAttireResults() {
        Response coverResponse = new Response();
        List<String> responseLinkList = new ArrayList<>();

        String sql = "SELECT response FROM business_attire;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            String responseLink = results.getString("response");
            responseLinkList.add(responseLink);
        }
        coverResponse.setResponseLinkList(responseLinkList);
        return coverResponse;
    }
    public Response fetchGeneralResults(){
        Response coverResponse = new Response();
        List<String> responseLinkList = new ArrayList<>();
        String sql = "SELECT * FROM pathway_general";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        if (result.next()) {
            String responseLink = result.getString("response");
            responseLinkList.add(responseLink);
        }
        coverResponse.setResponseLinkList(responseLinkList);
        return coverResponse;
    }



    // helper method
    public String mapToResponse(SqlRowSet results) {
    String response = results.getString("response");
        return response;
    }
    public String mapToResponseLink(SqlRowSet results) {

        String responseLink = results.getString("responseLinks");
        return responseLink;
    }
}
