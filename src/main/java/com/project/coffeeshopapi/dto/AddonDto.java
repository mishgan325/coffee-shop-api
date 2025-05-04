package com.project.coffeeshopapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

public class AddonDto {
    @JsonProperty("addon_id")
    private Long addonId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private Integer price;

    public AddonDto(Long addonId, String name, Integer price) {
        this.addonId = addonId;
        this.name = name;
        this.price = price;
    }

    public Long getAddonId() {
        return addonId;
    }

    public void setAddonId(Long addonId) {
        this.addonId = addonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
