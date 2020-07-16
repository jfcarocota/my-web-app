package com.matco.mywebapp.entities;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class Authorizator {

    public static final String API_KEY = "2B4B6250655367566B5970337336763979244226452948404D635166546A576D";
    
    public static boolean authorizeAPI(String token, String API_KEY){
        try{
            Jwts.parser().setSigningKey(API_KEY).parseClaimsJws(token).getBody();
        }catch(ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e){
            return false;
        }
        return true;
    }
}