package com.project.coffeeshopapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenDto {
    @JsonProperty("telegram_token")
    private String telegramToken;

    public String getTelegramToken() {
        return telegramToken;
    }

    public void setTelegramToken(String telegramToken) {
        this.telegramToken = telegramToken;
    }
}
