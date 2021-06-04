package com.techelevator.model;

import java.util.List;

public class Response {

    private List<String> responseTextList;
    private List<String> responseLinkList;

    public Response(){};

    public void setResponseTextList(List<String> responseTextList) {
        this.responseTextList = responseTextList;

    }

    public List<String> getResponseTextList() {
        return responseTextList;
    }

    public List<String> getResponseLinkList() {
        return responseLinkList;
    }

    public void setResponseLinkList(List<String> responseLinkList) {
        this.responseLinkList = responseLinkList;
    }
}
