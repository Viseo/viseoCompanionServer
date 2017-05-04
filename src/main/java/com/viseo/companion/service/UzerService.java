package com.viseo.companion.service;

import com.viseo.companion.dao.UzerRepository;
import com.viseo.companion.domain.Uzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UzerService {

    @Autowired
    private UzerRepository uzerRepository;

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

    public List<Uzer> getUserByEmail(String email) {
        return uzerRepository.getUserByEmail(email);
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
        Uzer uzer = uzerRepository.getUzer(userId);
        return uzer;
    }

    public List<Uzer> getUsers() {
        return (List<Uzer>) uzerRepository.getUzers();
    }

    public Uzer getUserIdByEmail(String email) {
        List<Uzer> list = getUserByEmail(email);
        return list.iterator().hasNext() ? list.iterator().next() : null;
    }
}
