package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    public void clear() {
        Storage.people.clear();
    }

    @Test
    void register_userAlreadyPresent_NotOk() {
        User user = new User();
        user.setLogin("user_login");
        user.setPassword("password");
        user.setAge(20);
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userNotPresent_Ok() {
        User user = new User();
        user.setLogin("user_login");
        user.setPassword("password");
        user.setAge(20);
        User user2 = new User();
        user2.setLogin("user_login_2");
        user2.setPassword("password");
        user2.setAge(20);
        Storage.people.add(user);
        registrationService.register(user2);
        int expected = 2;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void register_6charLogin_Ok() {
        User user = new User();
        user.setLogin("sumcal");
        user.setPassword("password");
        user.setAge(20);
        registrationService.register(user);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void register_8charLogin_Ok() {
        User user = new User();
        user.setLogin("sumcalma");
        user.setPassword("password");
        user.setAge(20);
        registrationService.register(user);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void register_5charLogin_NotOk() {
        User user = new User();
        user.setLogin("sumca");
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_3charLogin_NotOk() {
        User user = new User();
        user.setLogin("sum");
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_6charPassword_Ok() {
        User user = new User();
        user.setLogin("sumcal");
        user.setPassword("passwo");
        user.setAge(20);
        registrationService.register(user);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void register_8charPassword_Ok() {
        User user = new User();
        user.setLogin("sumcal");
        user.setPassword("password");
        user.setAge(20);
        registrationService.register(user);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void register_5charPassword_NotOk() {
        User user = new User();
        user.setLogin("sumcal");
        user.setPassword("passw");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_3charPassword_NotOk() {
        User user = new User();
        user.setLogin("sumcal");
        user.setPassword("pas");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age18_Ok() {
        User user = new User();
        user.setLogin("sumcal");
        user.setPassword("paswww");
        user.setAge(18);
        registrationService.register(user);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void register_age20_Ok() {
        User user = new User();
        user.setLogin("sumcal");
        user.setPassword("paswww");
        user.setAge(20);
        registrationService.register(user);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void register_age17_NotOk() {
        User user = new User();
        user.setLogin("sumcal");
        user.setPassword("paswww");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age15_NotOk() {
        User user = new User();
        user.setLogin("sumcal");
        user.setPassword("paswww");
        user.setAge(15);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
