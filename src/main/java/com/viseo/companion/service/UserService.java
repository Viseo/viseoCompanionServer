package com.viseo.companion.service;

import com.viseo.companion.dao.PasswordTokenDao;
import com.viseo.companion.dao.UserDao;
import com.viseo.companion.domain.PasswordResetToken;
import com.viseo.companion.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordTokenDao passwordTokenDao;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            user = userDao.addUser(user);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return user;
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User getUser(long userId) {
        try {
            return userDao.getUser(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByEmail(String email) {
        try {
            return userDao.getUserByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User updateUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userDao.updateUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(Long id) {
        try {
            User user = userDao.getUser(id);
            if (user != null) {
                userDao.deleteUser(user);
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
            PasswordResetToken myToken = passwordTokenDao.getTokenFromUserId(userId);
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
            passwordTokenDao.addToken(myToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void changePassword(long id, String password) {
        try {
            User user = userDao.getUser(id);
            if (user != null) {
                user.setPassword(passwordEncoder.encode(password));
                userDao.updateUser(user);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteToken(String token) {
        try {
            passwordTokenDao.deleteToken(token);
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
