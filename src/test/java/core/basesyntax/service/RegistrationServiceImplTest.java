package core.basesyntax.service;

import static core.basesyntax.service.RegistrationServiceImpl.MIN_USER_AGE;
import static core.basesyntax.service.RegistrationServiceImpl.MIN_USER_PASSWORD_LENGTH;
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
        for (int i = 0; i < 20; i++) {
            int invalidAge = 17 - i;
            user.setLogin("qq" + i * 10);
            user.setAge(invalidAge);
            assertThrows(RuntimeException.class, () -> registrationService.register(user),
                    "Age should not be lower " + MIN_USER_AGE + "\nyour age was" + (17 - i));
        }
    }

    @Test
    void register_validAge_ok() {
        user.setLogin("w");
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_sameUserLogin_notOk() {
        User userSameLogin = user.clone();
        userSameLogin.setLogin("validUserLogin");
        Storage.people.add(userSameLogin);
        try {
            registrationService.register(userSameLogin);
        } catch (RuntimeException e) {
            return;
        }
        fail("You should not be able register user with same login");
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
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Password should not be less then " + MIN_USER_PASSWORD_LENGTH);
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

    @Test
    void register_nullFieldUser_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Login should not be null");
        user.setLogin("validLogin");
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Password should not be null");
        user.setPassword("validPassword");
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Age should not be null");
    }
}
