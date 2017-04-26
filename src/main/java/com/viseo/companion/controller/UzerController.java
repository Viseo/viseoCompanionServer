package com.viseo.companion.controller;
import org.springframework.web.bind.annotation.PathVariable;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.UzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@RequestMapping("uzer")
@RestController
public class UzerController {


    @Autowired
    private UzerService uzerService;

    /*@RequestMapping(value = "${endpoint.addUser}", method = POST,consumes = { "application/json;charset=UTF-8" })
    public void addUser(@RequestBody final Uzer u){
        UzerService uzerService = new UzerService();
        uzerService.addUser(u);
    }*/
    @RequestMapping(value = "/adduser", method = RequestMethod.POST,  produces = { MediaType.APPLICATION_JSON_VALUE })
    public Uzer addUser(@RequestBody final  Uzer us) {
        return uzerService.addUser(us);

    }
    @RequestMapping(value = "${endpoint.authenticate}", method = POST)
    public void authenticate(BindingResult bindingResult){
    }

    @RequestMapping(value = "${endpoint.getUser}", method = GET)
    public void getUser(@PathVariable("userId") long userId){

    }

    @RequestMapping(value = "${endpoint.getUserByEmail}", method = GET)
    public void getUserByEmail(@PathVariable("pattern") String pattern){
    }

    @RequestMapping(value = "${endpoint.getUsers}", method = GET)
    public void getUsers() {
    }
}