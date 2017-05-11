package com.viseo.companion.controller;

import com.viseo.companion.domain.ResetPassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.UzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class UzerController {

    @Autowired
    private UzerService uzerService;

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping(value = "${endpoint.addUser}", method = POST)
    public Uzer addUser(@RequestBody Uzer us) {
        return uzerService.addUser(us);
    }

    @CrossOrigin
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
        return uzerService.getUserByEmail(pattern);
    }

    @RequestMapping(value = "${endpoint.resetPassword}", method = POST)
    public String resetPassword(HttpServletRequest request,
                                @RequestParam("email") String userEmail) {
        Uzer uzer = uzerService.getUserByEmail(userEmail);
        if (uzer == null) {
            throw new RuntimeException("User Not Found");
        }
        SimpleMailMessage email = createResetEmail(uzer, request);
        mailSender.send(email);
        return "Un mail vous a été envoyé";
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.changePassword}", method = POST)
    public boolean showChangePasswordPage(@RequestBody ResetPassword resetPassword) {
        if(uzerService.isTokenValid(resetPassword.getUzerId(), resetPassword.getTokenGuid())){
            uzerService.changePassword(resetPassword.getUzerId(), resetPassword.getPassword());
            uzerService.deleteToken(resetPassword.getTokenGuid());
            return true;
        }
        return false;
    }

    ///////////////////////////// NON-API METHODS //////////////////////////////

    private SimpleMailMessage createResetEmail(Uzer uzer, HttpServletRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("companionviseo@gmail.com");
        message.setTo(uzer.getEmail());
        message.setSubject("Viseo Companion: Création d'un nouveau mot de passe");
        String token = createToken(uzer);
        String contextPath = getAppUrl(request);
        String resetUrl = createResetURl(contextPath, uzer.getId(), token);
        String content = "Pour créer un nouveau mot de passe, merci de cliquer sur le lien suivant : " + resetUrl;
        message.setText(content);
        return message;
    }

    private String createResetURl(String contextPath, long id, String token) {
        return "http://localhost:3000/resetPassword?id=" + id + "&token=" + token;
//        return contextPath
//                + "/user/changePassword?id="
//                + id + "&token=" + token;
    }

    private String createToken(Uzer uzer) {
        String token = UUID.randomUUID().toString();
        uzerService.persistToken(uzer, token);
        return token;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}