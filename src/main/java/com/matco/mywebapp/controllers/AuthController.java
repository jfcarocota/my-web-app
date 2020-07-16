package com.matco.mywebapp.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.matco.mywebapp.dependencies.IAuth;
import com.matco.mywebapp.entities.Auth;
import com.matco.mywebapp.entities.Authorizator;
import com.matco.mywebapp.entities.Hash;
import com.matco.mywebapp.entities.Login;
import com.matco.mywebapp.entities.Session;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@CrossOrigin
public class AuthController {

    IAuth auth;

    public AuthController() {
        this.auth = new Auth();
    }

    @PostMapping("/authhash")
    public ResponseEntity<String> authHash(@RequestBody Hash hash) throws NoSuchAlgorithmException {
        
        if(!auth.verifySession(hash.getMessage())) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Session session = new Session("Jesus Caro", "jfcarocota@gmail.com");
        long time = System.currentTimeMillis();

        String token = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, Authorizator.API_KEY)
            .setId("matco")
            .setSubject("jfcarocota")
            .setIssuedAt(new Date(time))
            .setExpiration(new Date(time + 7000))
            .claim("session", session)
            .compact();
            
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/token")
    public ResponseEntity<String> tokinezer(@RequestHeader String authorization){
        if(!Authorizator.authorizeAPI(authorization, Authorizator.API_KEY)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Session session = new Session("Jesus Caro", "jfcarocota@gmail.com");
        long time = System.currentTimeMillis();

        String token = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, Authorizator.API_KEY)
            .setId("matco")
            .setSubject("jfcarocota")
            .setIssuedAt(new Date(time))
            .setExpiration(new Date(time + 7000))
            .claim("session", session)
            .compact();
            
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<String> authenticate(@RequestBody Login login){

        if(!auth.verifySession(login)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Session session = new Session("Jesus Caro", "jfcarocota@gmail.com");
        long time = System.currentTimeMillis();

        String token = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, Authorizator.API_KEY)
            .setId("matco")
            .setSubject("jfcarocota")
            .setIssuedAt(new Date(time))
            .setExpiration(new Date(time + 90000))
            .claim("session", session)
            .compact();
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/hellosec")
    public ResponseEntity<String> helloSec(@RequestHeader String token, @RequestHeader String API_KEY){
        boolean result = Authorizator.authorizeAPI(token, API_KEY);
        if(!result) return new ResponseEntity<>("No tienes permisos", HttpStatus.FORBIDDEN);

        return new ResponseEntity<>("Autorizado", HttpStatus.OK);
    }
}