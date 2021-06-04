package com.techelevator.controller;


import com.techelevator.dao.PathwayDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.RegisterUserDTO;
import com.techelevator.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ChatbotController {

    @Autowired
    PathwayDAO pathwayDAO;


    @RequestMapping(path = "/pathway/{topic}", method = RequestMethod.GET)
    public Response getPathwayInfoGivenTopic(@PathVariable String topic){
        Response response = pathwayDAO.getPathwayDetails(topic);
        return response;

    }

}
