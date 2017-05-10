package com.viseo.companion.service;

import com.viseo.companion.dao.UzerRepository;
import com.viseo.companion.dao.PasswordTokenRepository;
import com.viseo.companion.domain.PasswordResetToken;
import com.viseo.companion.domain.Uzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UzerService {

    @Autowired
    private UzerRepository uzerRepository;

    private PasswordTokenRepository passwordTokenRepository = new PasswordTokenRepository();

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Uzer addUser(Uzer uzer) {
        uzer.setPassword(passwordEncoder.encode(uzer.getPassword()));
        try {
            uzerRepository.addUzer(uzer);
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
        if (uzerRepository.getUzer(id) != null) {
            uzerRepository.deleteUzer(uzerRepository.getUzer(id));
            return true;
        }
        return false;
    }

    public Uzer getUserByEmail(String email) {
        return uzerRepository.getUserByEmail(email);
    }

    public Uzer checkCredentials(String email, String password) {
        Uzer uzer = getUserByEmail(email);
            if (passwordEncoder.matches(password, uzer.getPassword())) {
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

    public void createPasswordResetTokenForUser(Uzer uzer, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, uzer);
        passwordTokenRepository.addToken(myToken);
    }

//    public String validatePasswordResetToken(long id, String token){
//        PasswordResetToken passToken = getPassByToken(token);
//        if((passToken == null) || (passToken.getUzer().getId() != id)) {
//            return "invalidToken";
//        }
//
//    }


}
