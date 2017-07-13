package com.viseo.companion.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.viseo.companion.dao.PasswordTokenDAO;
import com.viseo.companion.dao.UserDAO;
import com.viseo.companion.domain.PasswordResetToken;
import com.viseo.companion.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordTokenDAO passwordTokenDAO;


    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            user = userDAO.addUser(user);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return user;
    }

    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    public User getUser(long userId) {
        try {
            return userDAO.getUser(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByEmail(String email) {
        try {
            return userDAO.getUserByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User updateUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userDAO.updateUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(Long id) {
        try {
            User user = userDAO.getUser(id);
            if (user != null) {
                userDAO.deleteUser(user);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User checkCredentials(String email, String password) {
        User user = getUserByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean resetPassword(User user, HttpServletRequest request) {
        SimpleMailMessage email = createResetEmail(user, request);
        try {
            mailSender.send(email);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

    public boolean isTokenValid(long userId, String tokenGuid) {
        try {
            PasswordResetToken myToken = passwordTokenDAO.getTokenFromUserId(userId);
            return myToken != null
                    && myToken.getGuid().equals(tokenGuid)
                    && myToken.isUnexpired();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void persistToken(User user, String token) {
        try {
            PasswordResetToken myToken = new PasswordResetToken(token, user);
            passwordTokenDAO.addToken(myToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void changePassword(long id, String password) {
        try {
            User user = userDAO.getUser(id);
            if (user != null) {
                user.setPassword(passwordEncoder.encode(password));
                userDAO.updateUser(user);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteToken(String token) {
        try {
            passwordTokenDAO.deleteToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SimpleMailMessage createResetEmail(User user, HttpServletRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("companionviseo@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Viseo Companion: Création d'un nouveau mot de passe");
        String token = createToken(user);
        String contextPath = getAppUrl(request);
        String resetUrl = createResetURl(contextPath, user.getId(), token);
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

    private String createToken(User user) {
        String token = UUID.randomUUID().toString();
        persistToken(user, token);
        return token;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }


}
