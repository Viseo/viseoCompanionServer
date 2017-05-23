package com.viseo.companion.controller;

import com.viseo.companion.domain.ResetPassword;
import com.viseo.companion.domain.User;
import com.viseo.companion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class UzerController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @RequestMapping(value = "${endpoint.addUser}", method = POST)
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getUser}", method = GET)
    public User getUser(@PathVariable("userId") long uzerId) {
        return userService.getUser(uzerId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getUsers}", method = GET)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getUserByEmail}", method = GET)
    public User getUserByEmail(@PathVariable("pattern") String pattern) {
        return userService.getUserByEmail(pattern);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.updateUser}", method = PUT)
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.deleteUser}", method = DELETE)
    public void deleteUser(@PathVariable(value = "userId") final long id) {
        userService.deleteUser(id);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.authenticate}", method = POST)
    public User authenticate(@RequestBody User user) {
        return userService.checkCredentials(user.getEmail(), user.getPassword());
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.resetPassword}", method = POST)
    public boolean resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            return false;
        }
        return userService.resetPassword(user, request);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.changePassword}", method = POST)
    public boolean showChangePasswordPage(@RequestBody ResetPassword resetPassword) {
        if (userService.isTokenValid(resetPassword.getUzerId(), resetPassword.getTokenGuid())) {
            userService.changePassword(resetPassword.getUzerId(), resetPassword.getPassword());
            userService.deleteToken(resetPassword.getTokenGuid());
            return true;
        }
        return false;
    }
}