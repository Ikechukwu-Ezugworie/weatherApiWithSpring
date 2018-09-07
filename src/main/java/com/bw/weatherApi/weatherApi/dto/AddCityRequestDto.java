package com.bw.weatherApi.weatherApi.dto;

import java.util.List;
import java.util.Set;

public class AddCityRequestDto {

    public List<Long> id;

    public List<Long> getId() {
        return id;
    }

    public void List(List<Long> id) {
        this.id = id;
    }
}
