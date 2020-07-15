package com.matco.mywebapp.controllers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.matco.mywebapp.dependencies.IAuth;
import com.matco.mywebapp.entities.Auth;
import com.matco.mywebapp.entities.Hash;
import com.matco.mywebapp.entities.Login;
import com.matco.mywebapp.entities.Session;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@RestController
@CrossOrigin
public class AuthController {

    IAuth auth;

    static final String API_KEY = "2B4B6250655367566B5970337336763979244226452948404D635166546A576D";

    public AuthController() {
        this.auth = new Auth();
    }

    @PostMapping("/authhash")
    public ResponseEntity<String> authHash(@RequestBody Hash hash) throws NoSuchAlgorithmException {
        
        if(!auth.verifySession(hash.getMessage())) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Session session = new Session("Jesus Caro", "jfcarocota@gmail.com");
        long time = System.currentTimeMillis();

        String token = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, API_KEY)
            .setId("matco")
            .setSubject("jfcarocota")
            .setIssuedAt(new Date(time))
            .setExpiration(new Date(time + 90000))
            .claim("session", session)
            .compact();
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /*@GetMapping("/hash")
    public ResponseEntity<String> getHash() {
        String originalString = "admin:admin";
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        byte[] hash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        String sha256hex = new String(Hex.encode(hash));
        return new ResponseEntity<>(sha256hex, HttpStatus.OK);
    }*/

   /* @PostMapping("/authhash")
    public ResponseEntity<String> authenticateHash(@RequestBody Hash hash) throws NoSuchAlgorithmException {
        if(!auth.verifySession(hash.getMessage())) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Session session = new Session("Jesus Caro", "jfcarocota@gmail.com");
        long time = System.currentTimeMillis();

        String token = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, API_KEY)
            .setId("matco")
            .setSubject("jfcarocota")
            .setIssuedAt(new Date(time))
            .setExpiration(new Date(time + 90000))
            .claim("session", session)
            .compact();
        return new ResponseEntity<>(token, HttpStatus.OK);
    }*/

    @PostMapping("/auth")
    public ResponseEntity<String> authenticate(@RequestBody Login login){

        if(!auth.verifySession(login)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Session session = new Session("Jesus Caro", "jfcarocota@gmail.com");
        long time = System.currentTimeMillis();

        String token = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, API_KEY)
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
        boolean result = authorizeAPI(token, API_KEY);
        if(!result) return new ResponseEntity<>("No tienes permisos", HttpStatus.FORBIDDEN);

        return new ResponseEntity<>("Autorizado", HttpStatus.OK);
    }

    boolean authorizeAPI(String token, String API_KEY){
        try{
            Jwts.parser().setSigningKey(API_KEY).parseClaimsJws(token).getBody();
        }catch(ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e){
            return false;
        }
        return true;
    }
}