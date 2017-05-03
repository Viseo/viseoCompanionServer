package com.viseo.companion.service;

import com.viseo.companion.dao.EventRepository;
import com.viseo.companion.dao.UzerRepository;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.exception.CompanionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * Created by HEL3666 on 24/04/2017.
 */
@Service

public class UzerService {


    @Autowired
    private UzerRepository uzerRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public Uzer addUser(Uzer uzer) {
        uzer.setPassword(passwordEncoder.encode(uzer.getPassword()));

        if (ExistsEmail(uzer.getEmail()) == true)
            return null;
        else
            return uzerRepository.save(uzer);

    }



    public Uzer updateUzer(Uzer uzer) {
        uzer.setPassword(passwordEncoder.encode(uzer.getPassword()));
        return uzerRepository.save(uzer);

    }

    public boolean deletUzer(Long id) {
        if (uzerRepository.exists(id)) {
            uzerRepository.delete(id);
            return true;
        }
        return false;
    }


    public Boolean ExistsEmail(String Email) {
        if (uzerRepository.getUserByEmail(Email).size() > 0)
            return true;
        else

            return false;
    }


    public List<Uzer> getUserByEmail(String email) {
        return uzerRepository.getUserByEmail(email);
    }


    public Uzer getEvent(long id) {
        return uzerRepository.findOne(id);
    }


    public boolean isUzerAlreadySaved(String email) {
        List<Uzer> list = getUserByEmail(email);
        return !list.isEmpty();
    }


    public Uzer checkCredentials(String email, String password) {

        Collection<Uzer> list = getUserByEmail(email);
        Uzer result = null;
        for (Uzer u : list) {
            if (passwordEncoder.matches(password, u.getPassword())) {
                result = u;
                break;
            }

        }
        return result;

    }


    public Uzer getUser(long userId) {
        Uzer uzer = uzerRepository.findOne(userId);
        if (uzer!=null) uzer.getRoles().size();
        return uzer;
    }


    public List<Uzer> getUsers() {
        return (List<Uzer>) uzerRepository.findAll();
    }


    public Uzer getUserIdByEmail(String email) {
        List<Uzer> list = getUserByEmail(email);
        return list.iterator().hasNext() ? list.iterator().next() : null;
    }

}
