package org.example.util;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class HmacUtil {

    private static final String HMAC_SHA256 = "HmacSHA256";

    public static String generateHmac(String data, String secretKey) throws Exception {
        Mac mac = Mac.getInstance(HMAC_SHA256);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), HMAC_SHA256);
        mac.init(secretKeySpec);

        byte[] rawHmac = mac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(rawHmac);
    }
}
