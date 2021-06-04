package com.techelevator.model;

import java.time.LocalDateTime;

public class Weather {
    String nameOfLocation;
    String stateLocated;
    LocalDateTime localDateAndTime;
    String currentTemperature;

    public String getNameOfLocation() {
        return nameOfLocation;
    }

    public void setNameOfLocation(String nameOfLocation) {
        this.nameOfLocation = nameOfLocation;
    }

    public String getStateLocated() {
        return stateLocated;
    }

    public void setStateLocated(String stateLocated) {
        this.stateLocated = stateLocated;
    }

    public LocalDateTime getLocalDateAndTime() {
        return localDateAndTime;
    }

    public void setLocalDateAndTime(LocalDateTime localDateAndTime) {
        this.localDateAndTime = localDateAndTime;
    }

    public String getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(String currentTemperature) {
        this.currentTemperature = currentTemperature;
    }
}
