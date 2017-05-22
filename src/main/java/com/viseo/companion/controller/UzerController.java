package com.viseo.companion.controller;

import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.UzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class UzerController {

    @Autowired
    private UzerService uzerService;

    @RequestMapping(value = "${endpoint.addUser}", method = POST)
    public Uzer addUser(@RequestBody Uzer us) {
        return uzerService.addUser(us);
    }

    @RequestMapping(value = "${endpoint.authenticate}", method = POST)
    public Uzer authenticate(@RequestBody Uzer user) {
        return uzerService.checkCredentials(user.getEmail(), user.getPassword());
    }

    @RequestMapping(value = "${endpoint.getUser}", method = GET)
    public Uzer getUser(@PathVariable("userId") long userId) {
        return uzerService.getUser(userId);
    }

    @RequestMapping(value = "${endpoint.getUsers}", method = GET)
    public final List<Uzer> getUsers() {
        return uzerService.getUsers();
    }

    @RequestMapping(value = "${endpoint.deleteUser}", method = DELETE)
    public final void deleteUser(@PathVariable(value = "userId")  final long id) {
        uzerService.deleteUzer(id);
    }

    @RequestMapping(value = "${endpoint.updateUser}", method = PUT)
    public final Uzer updateUser(@RequestBody Uzer use) {
        Uzer uzer = null;
        try {
            uzer = uzerService.updateUzer(use);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return uzer;
    }

    @RequestMapping(value = "${endpoint.getUserByEmail}", method = GET)
    public Uzer getUserByEmail(@PathVariable("pattern") String pattern) {
        return uzerService.getUserIdByEmail(pattern);
    }
}