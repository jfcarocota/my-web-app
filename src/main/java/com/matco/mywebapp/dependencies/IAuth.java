package com.matco.mywebapp.dependencies;

import com.matco.mywebapp.entities.Login;

public interface IAuth {
    
    public boolean verifySession(Login login);
}