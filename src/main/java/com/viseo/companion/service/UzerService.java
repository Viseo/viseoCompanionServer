package com.viseo.companion.service;

import com.viseo.companion.dao.EventRepository;
import com.viseo.companion.dao.UzerRepository;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
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
    BCryptPasswordEncoder passwordEncoder;

    public void addUser(long id, String email, String password, String username) {
        Uzer uzer = new Uzer();
        uzer.setEmail(email);
        uzer.setPassword(password);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        uzer.setPassword(passwordEncoder.encode(uzer.getPassword()));
        uzerRepository.save(uzer);

    }

    public  List<Uzer> getUserByEmail(String email) {

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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Collection<Uzer> list = getUserByEmail(email);
        if(list.iterator().hasNext()) {
            Uzer user = list.iterator().next();
            if(encoder.matches(password, user.getPassword()))
                return user;
        }
        return  null;
    }


    public Uzer getUser(long userId) {
        return uzerRepository.findOne(userId);
    }

    public List<Uzer> getUsers() {
        return (List<Uzer>)uzerRepository.findAll();
    }



    public Uzer getUserIdByEmail(String email) {
        List<Uzer> list = getUserByEmail(email);
        return list.iterator().hasNext() ? list.iterator().next() : null;
    }

}
