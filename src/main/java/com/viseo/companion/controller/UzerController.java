package com.viseo.companion.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class UzerController {

    @RequestMapping(value = "${endpoint.addUser}", method = POST)
    public void addUser(BindingResult bindingResult){
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