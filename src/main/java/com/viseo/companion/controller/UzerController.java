package com.viseo.companion.controller;
import org.springframework.web.bind.annotation.PathVariable;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.UzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
public class UzerController {


    @Autowired
    private UzerService uzerService;

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    @ResponseBody
    public Uzer addUser(@RequestBody Uzer us) {
        return uzerService.addUser(us);

    }
    @RequestMapping(value = "${endpoint.authenticate}", method = POST)
    public void authenticate(BindingResult bindingResult){
    }

    @RequestMapping(value = "/users/{userId}", method = GET)
    @ResponseBody
    public Uzer getUser(@PathVariable("userId") long userId){
        return uzerService.getUser(userId);

    }

    @RequestMapping(value = "${endpoint.getUserByEmail}", method = GET)
    public void getUserByEmail(@PathVariable("pattern") String pattern){
    }

    @RequestMapping(value = "${endpoint.getUsers}", method = GET)
    public void getUsers() {
    }
}