package com.project.coffeeshopapi.dto;

import lombok.Data;

import java.util.List;


public class OrderRequest {
    private String telegram_token;
    private List<OrderItemRequest> items;

    public String getTelegram_token() {
        return telegram_token;
    }

    public void setTelegram_token(String telegram_token) {
        this.telegram_token = telegram_token;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

    public static class OrderItemRequest {
        private Long coffee_id;
        private List<Long> addon_ids;

        public Long getCoffee_id() {
            return coffee_id;
        }

        public void setCoffee_id(Long coffee_id) {
            this.coffee_id = coffee_id;
        }

        public List<Long> getAddon_ids() {
            return addon_ids;
        }

        public void setAddon_ids(List<Long> addon_ids) {
            this.addon_ids = addon_ids;
        }
    }
}

