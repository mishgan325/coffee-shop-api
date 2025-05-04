package com.project.coffeeshopapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InitDataRequest {
    @JsonProperty("init_data")
    private String initData;

    public String getInitData() {
        return initData;
    }

    public void setInitData(String initData) {
        this.initData = initData;
    }
}

