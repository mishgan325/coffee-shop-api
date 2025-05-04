package com.project.coffeeshopapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.coffeeshopapi.models.Addon;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;

import java.util.List;

public class CoffeeDto {
    @JsonProperty("coffee_id")
    private Long coffeeId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("price")
    private Integer price;
    @JsonProperty("addons")
    private List<AddonDto> addons;

    public CoffeeDto(Long coffeeId,
                     String name,
                     String description,
                     String imageUrl,
                     Integer price,
                     List<AddonDto> addons) {
        this.coffeeId = coffeeId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.addons = addons;
    }

    public Long getCoffeeId() {
        return coffeeId;
    }

    public void setCoffeeId(Long coffeeId) {
        this.coffeeId = coffeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<AddonDto> getAddons() {
        return addons;
    }

    public void setAddons(List<AddonDto> addons) {
        this.addons = addons;
    }
}
