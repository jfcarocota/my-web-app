package com.matco.mywebapp.entities;

public class Hash {
    String message;

    public Hash(String message) {
        this.message = message;
    }
    
    public Hash(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}