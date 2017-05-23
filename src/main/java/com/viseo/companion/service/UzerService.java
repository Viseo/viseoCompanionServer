package com.viseo.companion.service;

import com.viseo.companion.dao.PasswordTokenRepository;
import com.viseo.companion.dao.UzerRepository;
import com.viseo.companion.domain.PasswordResetToken;
import com.viseo.companion.domain.Uzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Service
public class UzerService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UzerRepository uzerRepository;

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Uzer addUser(Uzer uzer) {
        uzer.setPassword(passwordEncoder.encode(uzer.getPassword()));
        try {
            uzer = uzerRepository.addUzer(uzer);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return uzer;
    }

    public Uzer updateUzer(Uzer uzer) {
        uzer.setPassword(passwordEncoder.encode(uzer.getPassword()));
        return uzerRepository.updateUzer(uzer);
    }

    public boolean deleteUzer(Long id) {
        return uzerRepository.getUzer(id) != null && uzerRepository.deleteUzer(uzerRepository.getUzer(id));
    }

    public Uzer getUserByEmail(String email) {
        return uzerRepository.getUserByEmail(email);
    }

    public Uzer checkCredentials(String email, String password) {
        Uzer uzer = getUserByEmail(email);
        if (uzer != null && passwordEncoder.matches(password, uzer.getPassword())) {
            return uzer;
        }
        return null;
    }

    public Uzer getUser(long userId) {
        Uzer uzer = uzerRepository.getUzer(userId);
        return uzer;
    }

    public List<Uzer> getUsers() {
        return (List<Uzer>) uzerRepository.getUzers();
    }

    public boolean resetPassword(Uzer uzer, HttpServletRequest request) {
        SimpleMailMessage email = createResetEmail(uzer, request);
        try {
            mailSender.send(email);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

    public void persistToken(Uzer uzer, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, uzer);
        passwordTokenRepository.addToken(myToken);
    }

    public boolean isTokenValid(long uzerId, String tokenGuid) {
        PasswordResetToken myToken = passwordTokenRepository.getTokenFromUzerId(uzerId);
        return myToken != null
                && myToken.getGuid().equals(tokenGuid)
                && myToken.isUnexpired();
    }

    public void changePassword(long id, String password) {
        Uzer uzer = uzerRepository.getUzer(id);
        if (uzer != null) {
            uzer.setPassword(passwordEncoder.encode(password));
            uzerRepository.updateUzer(uzer);
        }
    }


    public void deleteToken(String token) {
        passwordTokenRepository.deleteToken(token);
    }

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
        persistToken(uzer, token);
        return token;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
