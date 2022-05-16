package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(20);
    }

    @Test
    void register_lowAge_notOk() {
        user.setLogin("q");
        user.setAge(17);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            System.out.println("Ok, got exception " + e);
            return;
        }
        fail("Should got exception for age: 17");
    }

    @Test
    void register_enoughAge_ok() {
        user.setLogin("w");
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_sameLoginUser_notOk() {
        User userSameLogin = new User();
        userSameLogin.setLogin("validLogin");
        registrationService.register(user);
        try {
            registrationService.register(userSameLogin);
        } catch (RuntimeException e) {
            System.out.println("Ok, got exception " + e);
            return;
        }
        fail("You cant register user with same login");
    }

    @Test
    void register_severalUsers_ok() {
        for (int i = 0; i < 10; i++) {
            User newUser = user.clone();
            newUser.setLogin("validLogin" + i);
            int previousSize = Storage.people.size();
            registrationService.register(newUser);
            assertEquals(previousSize + 1, Storage.people.size(),
                    "The storage size expected to grow after registration");
        }
    }

    @Test
    void register_invalidPassword_notOk() {
        user.setLogin("q2");
        user.setPassword("qwert");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validPassword() {
        for (int i = 0; i < 10; i++) {
            User newUser = user.clone();
            newUser.setLogin("validLogin" + i + 10);
            newUser.setPassword("valid$" + i);
            int previousSize = Storage.people.size();
            registrationService.register(newUser);
            assertEquals(previousSize + 1, Storage.people.size(),
                    "The storage size expected to grow after registration");
        }

    }
}
