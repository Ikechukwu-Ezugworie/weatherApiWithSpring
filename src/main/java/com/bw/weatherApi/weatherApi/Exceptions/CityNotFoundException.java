package com.bw.weatherApi.weatherApi.Exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public class CityNotFoundException extends RuntimeException {
   private String message;
   private List<Long> notFoudCityIds;
   private Long cityId;
   private HttpStatus status;

   public CityNotFoundException(String message,  List<Long> notFoudCityIds, HttpStatus status){
       this.notFoudCityIds = notFoudCityIds;
       this.message = message;
       this.status = status;
   }

   public CityNotFoundException(String message,Long cityId, HttpStatus status){
       this.message = message;
       this.cityId = cityId;
       this.status = status;
   }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Long> getNotFoudCityIds() {
        return notFoudCityIds;
    }

    public void setNotFoudCityIds(List<Long> notFoudCityIds) {
        this.notFoudCityIds = notFoudCityIds;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
