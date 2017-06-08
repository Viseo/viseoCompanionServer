package com.viseo.companion.service;

import com.viseo.companion.domain.Role;
import com.viseo.companion.domain.User;
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
public class UserServiceTest {

    @Autowired
    private UserService userService;





    @Test
    public void addUserTest() {
        final User user = new User();
        user.setEmail("me@gmail.com");
        user.setPassword("123456");
        user.setFirstName("meee");
        user.setLastName("meee");
        try {
            final User newUser = userService.addUser(user);
            Assert.assertNotNull(newUser.getId());
            Assert.assertEquals(user.getEmail(), newUser.getEmail());
        } catch (final CompanionException ex) {
            Assert.assertEquals("the User that you want to add already exist ", ex.getMessage());
        }
    }

    @Test
    public final void updateUzerTest() {
        final Long id = 34L;

        final User user = userService.getUser(id);
        user.setEmail("woooo@gmail.com");
        user.setPassword("haifa");
        user.setFirstName("haifaaa");
        user.setLastName("ontt");

        Role role = new Role("admin", "admin");
        user.getRoles().add(role);
        User newUser = userService.updateUser(user);
        try {
            Assert.assertEquals(user.getId(), newUser.getId());
        } catch (final CompanionException ex) {
            Assert.assertEquals("l'user que vous souhaitez modifier n'exsite pas", ex.getMessage());
        }
    }

    @Test
    public void checkCredentialsTest() {
        final User uzer = new User();
        uzer.setEmail("woooo@gmail.com");
        uzer.setPassword("leo");
        User user = userService.checkCredentials(uzer.getEmail(), uzer.getPassword());
        try {
            Assert.assertNotNull(user);
        } catch (final CompanionException ex) {
            Assert.assertEquals("le mot de passe que vous avez entrer est incorret ", ex.getMessage());
        }
    }

    @Test
    public final void deleteUzerTest() {
        final Long id = 35L;
        final User user = userService.getUser(id);
        try {
            userService.deleteUser(id);
        } catch (final CompanionException ex) {
            Assert.assertEquals("This user cannot be deleted", ex.getMessage());
        }
    }

    @Test
    public void getUserTest() {
        try {
            Assert.assertNotNull(userService.getUser(19L));
        } catch (final CompanionException ex) {
            Assert.assertEquals("l'utilisateur n'existe pas ", ex.getMessage());
        }
    }

    @Test
    public void getUsersTest() {
        final List<User> listUsers = userService.getUsers();
        Assert.assertNotNull(listUsers);
        Assert.assertEquals(6, listUsers.size());
    }

    @Test
    public void getUserByEmailTest() {
        final User User = userService.getUserByEmail("woooo@gmail.com");
        Assert.assertNotNull(User);
    }
}
