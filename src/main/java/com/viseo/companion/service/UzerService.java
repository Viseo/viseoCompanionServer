package com.viseo.companion.service;

import com.viseo.companion.dao.EventRepository;
import com.viseo.companion.dao.UzerRepository;
import com.viseo.companion.domain.Uzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by HEL3666 on 24/04/2017.
 */
@Service
public class UzerService {


    @Autowired
    private UzerRepository uzerRepository;
    BCryptPasswordEncoder passwordEncoder;

    public void addUser(long id, String email, String password, String username) {
        Uzer uzer = new Uzer();
        uzer.setEmail(email);
        uzer.setPassword(password);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        uzer.setPassword(passwordEncoder.encode(uzer.getPassword()));
        uzerRepository.save(uzer);

    }


}
