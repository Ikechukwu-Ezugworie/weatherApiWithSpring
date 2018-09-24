package com.bw.weatherApi.weatherApi.dto;

public class WeatherResponseDto {

    private Current current;

    public WeatherResponseDto() {
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }


    @Override
    public String toString() {
        return "WeatherResponseDto{" +
                "current=" + current +
                '}';
    }
}
