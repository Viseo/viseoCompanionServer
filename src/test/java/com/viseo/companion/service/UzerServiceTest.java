package com.viseo.companion.service;

import com.viseo.companion.ViseocompanionserverApplication;
import com.viseo.companion.domain.Role;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.exception.CompanionException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
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
        } catch (final CompanionException ex) {
            Assert.assertEquals("the Uzer that you want to add already exist ", ex.getMessage());
        }
    }

    @Test
    public final void updateUzerTest() {
        final Long id = 34L;

        final Uzer uzer = uzerService.getUser(id);
        uzer.setEmail("woooo@gmail.com");
        uzer.setPassword("haifa");
        uzer.setFirstName("haifaaa");
        uzer.setLastName("ontt");

        Role role = new Role("admin", "admin");
        uzer.getRoles().add(role);
        Uzer newUzer = uzerService.updateUzer(uzer);
        try {
            Assert.assertEquals(uzer.getId(), newUzer.getId());
        } catch (final CompanionException ex) {
            Assert.assertEquals("l'uzer que vous souhaitez modifier n'exsite pas", ex.getMessage());
        }
    }

    @Test
    public void checkCredentialsTest() {
        final Uzer uzer = new Uzer();
        uzer.setEmail("woooo@gmail.com");
        uzer.setPassword("leo");
        Uzer user = uzerService.checkCredentials(uzer.getEmail(), uzer.getPassword());
        try {
            Assert.assertNotNull(user);
        } catch (final CompanionException ex) {
            Assert.assertEquals("le mot de passe que vous avez entrer est incorret ", ex.getMessage());
        }
    }

    @Test
    public final void deleteUzerTest() {
        final Long id = 35L;
        final Uzer uzer = uzerService.getUser(id);
        try {
            uzerService.deleteUzer(id);
        } catch (final CompanionException ex) {
            Assert.assertEquals("This user cannot be deleted", ex.getMessage());
        }
    }

    @Test
    public void getUserTest() {
        try {
            Assert.assertNotNull(uzerService.getUser(19L));
        } catch (final CompanionException ex) {
            Assert.assertEquals("l'utilisateur n'existe pas ", ex.getMessage());
        }
    }

    @Test
    public void getUsersTest() {
        final List<Uzer> listUsers = uzerService.getUsers();
        Assert.assertNotNull(listUsers);
        Assert.assertEquals(6, listUsers.size());
    }

    @Test
    public void getUserByEmailTest() {
        final Uzer User = uzerService.getUserByEmail("woooo@gmail.com");
        Assert.assertNotNull(User);
    }
}
