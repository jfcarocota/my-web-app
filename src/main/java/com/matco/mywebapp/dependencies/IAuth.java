package com.matco.mywebapp.dependencies;

import java.security.NoSuchAlgorithmException;

import com.matco.mywebapp.entities.Login;

public interface IAuth {
    
    public boolean verifySession(Login login);
    public boolean verifySession(String hash) throws NoSuchAlgorithmException;
}