package com.matco.mywebapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;

import com.matco.mywebapp.entities.Authorizator;
import com.matco.mywebapp.entities.Person;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PersonController {

    String key = "sofia";

    List<Person> persons = new ArrayList<>();


    public PersonController(){
        persons.add(new Person(1,"Jesus", 28));
        persons.add(new Person(2, "Julio", 34));
        persons.add(new Person(3, "Javier", 27));
    }
    
    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getPersons(@RequestHeader String authorization){
        if(!Authorizator.authorizeAPI(authorization, Authorizator.API_KEY)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        return new ResponseEntity<>( persons, HttpStatus.OK);
    } 

    @GetMapping("/persons/{id}")
    public Person getPersons(@PathVariable("id") int id){

        return persons.get(id);
    } 

    @PostMapping("/persons") // post 1
    public Person setPerson(@RequestBody Person person){
        persons.add(person);
        return person;
    }

    @GetMapping("/secure")
    public String getSecure(@RequestHeader String key){

        if(!key.equals(this.key)) return "no tienes permiso";

        return "puedes ver esto";
    }
}