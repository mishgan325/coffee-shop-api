package com.project.coffeeshopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramInitData {
    private String id;
    private String first_name;
    private String last_name;
    private String username;
    private String photo_url;
    private String auth_date;
    private String hash;
}
