package com.viseo.companion.controller;

import com.viseo.companion.domain.GenericResponse;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
    public GenericResponse resetPassword(HttpServletRequest request,
                                         @RequestParam("email") String userEmail) {
        Uzer uzer = uzerService.getUserByEmail(userEmail);

        if (uzer == null) {
            throw new RuntimeException("User Not Found");
        }
        String token = UUID.randomUUID().toString();
        uzerService.createPasswordResetTokenForUser(uzer, token);
        mailSender.send(constructResetTokenEmail(getAppUrl(request), token, uzer));
        return new GenericResponse("Reinitialisation de mot de passe");
    }

    @RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(Model model,
                                         @RequestParam("id") long id, @RequestParam("token") String token) {
        String result = null;
//        String result = uzerService.validatePasswordResetToken(id, token);
        if(result != null){
            System.out.println("Success");
            return "redirect:/PassChangeSuccessful";
        }
        System.out.println("Fail");
        return "redirect:/updatePassword.html";
    }


    private SimpleMailMessage constructResetTokenEmail(String contextPath, String token, Uzer uzer) {
        String url = contextPath + "/user/changePassword?id=" +
                uzer.getId() + "&token=" + token;
        String message = "Reinitialisation de mot de passe";
        return constructEmail("Reset Password", message + " \r\n" + url, uzer);
    }

    private SimpleMailMessage constructEmail(String subject, String body, Uzer uzer) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(uzer.getEmail());
        email.setFrom("${support.email}");
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}