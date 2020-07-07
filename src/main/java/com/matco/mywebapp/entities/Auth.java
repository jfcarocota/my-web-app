package com.matco.mywebapp.entities;

import com.matco.mywebapp.dependencies.IAuth;

public class Auth implements IAuth {

    @Override
    public boolean verifySession(Login login) {
        
        return login.getUsername().equals("admin") && login.getPassword().equals("admin");
    }
}