package com.viseo.companion.service;

import com.viseo.companion.ViseocompanionserverApplication;
import com.viseo.companion.dao.UzerRepository;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Role;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.exception.CompanionException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * Created by HEL3666 on 24/04/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ViseocompanionserverApplication.class)
public class UzerServiceTest {

    @Autowired
    private UzerService uzerService;

    @Test
    public void addUserTest() {
        final Uzer uzer = new Uzer();
        uzer.setEmail("me@gmail.com");
        uzer.setPassword("me");
        uzer.setFirstName("meee");
        uzer.setLastName("meee");
        try {

            final Uzer newUzer = uzerService.addUser(uzer);
            Assert.assertNotNull(newUzer.getId());
            Assert.assertEquals(uzer.getEmail(), newUzer.getEmail());
            // Assert.fail();
        } catch (final CompanionException ex) {
            Assert.assertEquals("l'utilisateur que vous souhaitez ajouter exsite déja ", ex.getMessage());
        }

    }
    @Test
    public final void updateUzerTest() {
        final Long id = 19L;

        final Uzer uzer = uzerService.getUser(id);
        uzer.setEmail("ibal@gmail.com");
        uzer.setPassword("ib");
        uzer.setFirstName("ib");
        uzer.setLastName("bst");
        Uzer user = uzerService.getUser(18L);
        Role role = new Role("admin", "admin");
        user.getRoles().add(role);

        Uzer newUzer= uzerService.updateUzer(uzer);

        try {


            // Assert.assertNotNull(newEvent.getId());
            Assert.assertEquals(uzer.getId(), newUzer.getId());
            // Assert.fail();
        } catch (final CompanionException ex) {
            Assert.assertEquals("l'uzer que vous souhaitez modifier n'exsite pas", ex.getMessage());
        }
    }

    @Test
    public void checkCredentialsTest() {
        final Uzer uzer = new Uzer();
        uzer.setEmail("ihate@gmail.com");
        uzer.setPassword("me");

        Uzer user = uzerService.checkCredentials(uzer.getEmail(), uzer.getPassword());

        try {

            Assert.assertNotNull(user);

            // Assert.fail();
        } catch (final CompanionException ex) {
            Assert.assertEquals("le mot de passe que vous avez entrer est incorret ", ex.getMessage());
        }


    }
    @Test
    public final void  deletUzerTest() {
        final Long id = 12L;
        final Uzer uzer= uzerService.getUser(id);
        try {
            uzerService. deletUzer(id);
        } catch (final CompanionException  ex) {
            Assert.assertEquals("Cant delete utilisateur", ex.getMessage());
        }
    }

    @Test
    public void getUserTest() {

        try {
            Assert.assertNotNull(uzerService.getUser(12L));
        } catch (final CompanionException ex) {
            Assert.assertEquals("l'utilisateur n'existe pas ", ex.getMessage());
        }

    }

    @Test
    public void getUsersTest(){


        final List<Uzer> listUsers = uzerService.getUsers();
        Assert.assertNotNull(listUsers);
        Assert.assertEquals(4, listUsers.size());
    }

   @Test
    public void getUserByEmailTest(){
        final List<Uzer> listUsers = uzerService.getUserByEmail("ibtisami@gmail.com");
        Assert.assertNotNull(listUsers);
        Assert.assertEquals(1, listUsers.size());
    }




}
