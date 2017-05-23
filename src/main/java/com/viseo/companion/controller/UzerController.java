package com.viseo.companion.controller;

import com.viseo.companion.domain.ResetPassword;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.UzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class UzerController {

    @Autowired
    private UzerService uzerService;

    @CrossOrigin
    @RequestMapping(value = "${endpoint.addUser}", method = POST)
    public Uzer addUser(@RequestBody Uzer uzer) {
        return uzerService.addUser(uzer);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.authenticate}", method = POST)
    public Uzer authenticate(@RequestBody Uzer user) {
        return uzerService.checkCredentials(user.getEmail(), user.getPassword());
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getUser}", method = GET)
    public Uzer getUser(@PathVariable("uzerId") long uzerId) {
        return uzerService.getUser(uzerId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getUsers}", method = GET)
    public List<Uzer> getUsers() {
        return uzerService.getUsers();
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.deleteUser}", method = DELETE)
    public boolean deleteUser(@PathVariable(value = "uzerId") final long id) {
        return uzerService.deleteUzer(id);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.updateUser}", method = PUT)
    public Uzer updateUser(@RequestBody Uzer use) {
        Uzer uzer = null;
        try {
            uzer = uzerService.updateUzer(use);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return uzer;
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getUserByEmail}", method = GET)
    public Uzer getUserByEmail(@PathVariable("pattern") String pattern) {
        return uzerService.getUserByEmail(pattern);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.resetPassword}", method = POST)
    public boolean resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        Uzer uzer = uzerService.getUserByEmail(userEmail);
        if (uzer == null) {
            return false;
        }
        return uzerService.resetPassword(uzer, request);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.changePassword}", method = POST)
    public boolean showChangePasswordPage(@RequestBody ResetPassword resetPassword) {
        if (uzerService.isTokenValid(resetPassword.getUzerId(), resetPassword.getTokenGuid())) {
            uzerService.changePassword(resetPassword.getUzerId(), resetPassword.getPassword());
            uzerService.deleteToken(resetPassword.getTokenGuid());
            return true;
        }
        return false;
    }
}