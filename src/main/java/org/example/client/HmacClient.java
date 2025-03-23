package org.example.client;

import org.example.util.HmacUtil;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HmacClient {

    public static void main(String[] args) throws Exception {
        String secretKey = "YourSecretKey";
        String jsonBody = "{\"message\":\"Hello, HMAC!\"}";
        String timestamp = String.valueOf(System.currentTimeMillis());

        String dataToSign = timestamp + jsonBody;
        String signature = HmacUtil.generateHmac(dataToSign, secretKey);

        URL url = new URL("http://localhost:8080/api/your-endpoint");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("X-Timestamp", timestamp);
        connection.setRequestProperty("X-Signature", signature);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
    }
}

