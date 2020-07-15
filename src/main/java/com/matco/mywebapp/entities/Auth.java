package com.matco.mywebapp.entities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.matco.mywebapp.dependencies.IAuth;

import org.bouncycastle.util.encoders.Hex;

public class Auth implements IAuth {

    @Override
    public boolean verifySession(Login login) {

        return login.getUsername().equals("admin") && login.getPassword().equals("admin");
    }

    @Override
    public boolean verifySession(String hash) throws NoSuchAlgorithmException {
        String orginalString = "admin:admin";
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        byte[] hashdigest = messageDigest.digest(orginalString.getBytes(StandardCharsets.UTF_8));
        String sha256hex = new String(Hex.encode(hashdigest));
        return hash.endsWith(sha256hex);
    }
}