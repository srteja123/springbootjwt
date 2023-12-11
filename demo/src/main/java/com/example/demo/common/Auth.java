package com.example.demo.common;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Auth {
    private static final String HMAC_SHA512 = "HmacSHA512";

    public static HashMap<String, String> create_auth_token(Long user_id, int lifetime) {
        String user_id_string = String.valueOf(user_id);
        HashMap<String, String> auth_response = new HashMap<String,String>();
        auth_response.put("token", user_id_string+"_"+TimeStamp.fetch_present_timestamp()+"_"+TimeStamp.fetch_future_timestamp(lifetime)+"_"+RandomString.getAlphaNumericString(100));
        auth_response.put("token_created_time", TimeStamp.fetch_present_string_time());
        auth_response.put("token_expiry_time", TimeStamp.fetch_future_string_time(lifetime));
        return auth_response;
    }

    public static String generate_signature(String data) {
        Mac sha512Hmac;
        String result = "";
        final String key = "Welcome1";

        try {
            final byte[] byteKey = key.getBytes(StandardCharsets.UTF_8);
            sha512Hmac = Mac.getInstance(HMAC_SHA512);
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
            sha512Hmac.init(keySpec);
            byte[] macData = sha512Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            // Can either base64 encode or put it right into hex
            result = Base64.getEncoder().encodeToString(macData);
            //result = bytesToHex(macData);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Long fetch_user_id_from_auth_header(String auth_header) {
        String[] auth = auth_header.split(" ");
        String full_encoded_token = auth[1];
        String encoded_token = full_encoded_token.split("[.]")[0];
        byte[] decodedByteArr = Base64.getDecoder().decode(encoded_token);
        String decoded_token = new String(decodedByteArr);
        Long user_id = Long.parseLong(decoded_token.split("_")[0]);
        return user_id;
    }
    
}
