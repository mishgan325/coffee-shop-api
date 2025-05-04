package com.project.coffeeshopapi.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TelegramAuthValidator {

    @Value("${telegram.bot.token}")
    private String botToken;

    public boolean isValid(String initData) {
        try {
            String decoded = URLDecoder.decode(initData, StandardCharsets.UTF_8);

            List<String> params = new ArrayList<>(Arrays.asList(decoded.split("&")));

            String hashEntry = params.stream()
                    .filter(s -> s.startsWith("hash="))
                    .findFirst()
                    .orElse(null);

            if (hashEntry == null) return false;
            params.remove(hashEntry);
            String providedHash = hashEntry.substring("hash=".length());

            params.sort(String::compareTo);

            String dataCheckString = String.join("\n", params);

            Mac hmacKey = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec("WebAppData".getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacKey.init(secretKeySpec);
            byte[] key = hmacKey.doFinal(botToken.getBytes(StandardCharsets.UTF_8));

            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec hmacSecretKey = new SecretKeySpec(key, "HmacSHA256");
            hmac.init(hmacSecretKey);
            byte[] result = hmac.doFinal(dataCheckString.getBytes(StandardCharsets.UTF_8));
            String calculatedHash = HexFormat.of().formatHex(result);

            return calculatedHash.equals(providedHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

