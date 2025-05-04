package com.project.coffeeshopapi.dto;

import java.time.LocalDateTime;
import java.util.List;


public class UserOrderResponse {
    private Long id;
    private LocalDateTime created_at;
    private List<Item> items;
    private Integer total_price;

    public UserOrderResponse(Long id, LocalDateTime created_at, List<Item> items, Integer total_price) {
        this.id = id;
        this.created_at = created_at;
        this.items = items;
        this.total_price = total_price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Integer total_price) {
        this.total_price = total_price;
    }

    public static class Item {
        private String coffee_name;
        private List<String> addons;

        public Item(String coffee_name, List<String> addons) {
            this.coffee_name = coffee_name;
            this.addons = addons;
        }

        public String getCoffee_name() {
            return coffee_name;
        }

        public void setCoffee_name(String coffee_name) {
            this.coffee_name = coffee_name;
        }

        public List<String> getAddons() {
            return addons;
        }

        public void setAddons(List<String> addons) {
            this.addons = addons;
        }
    }
}

