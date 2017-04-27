package com.viseo.companion.controller;
import org.springframework.web.bind.annotation.PathVariable;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.UzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;

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
    @RequestMapping(value = "/authenticate", method = POST)
    public  Uzer authenticate(@PathVariable("email,password")String email, String password){

        return uzerService.checkCredentials(email,password);


    }

    @RequestMapping(value = "/users/{userId}", method = GET)
    @ResponseBody
    public Uzer getUser(@PathVariable("userId") long userId){
        return uzerService.getUser(userId);

    }



    @RequestMapping(value = "/users/{email}", method = GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public  List<Uzer> getUserByEmail(@PathVariable(value = "email") String email){
        return uzerService.getUserByEmail(email);
    }





    @RequestMapping(value = "/users", method =  RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })

    public final List<Uzer> getUsers() {
        return uzerService.getUsers();

    }
}