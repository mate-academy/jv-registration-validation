package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static List<User> peopleCopy;
    private static RegistrationServiceImpl registration;
    private User user;

    @BeforeAll
    public static void setUp() {
        registration = new RegistrationServiceImpl();
        peopleCopy = new ArrayList<>();
    }

    @BeforeEach
    void initValidUser() {
        user = initUser();
    }

    @AfterEach
    void removeAllFromPeople() {
        Storage.people.clear();
    }

    @Test
    void when_validUser_Ok() {
        assertEquals(registration.register(user), user);
    }

    @Test
    void when_loginNull_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void when_passwordNull_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void when_ageNull_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void when_userNull_notOk() {
        user = null;
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void when_loginLength6_Ok() {
        user.setLogin("123456");
        assertEquals(registration.register(user), user);
    }

    @Test
    void when_loginLength5_notOk() {
        user.setLogin("12345");
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void when_loginLength7_Ok() {
        user.setLogin("1234567");
        assertEquals(registration.register(user), user);
    }

    @Test
    void when_passwordLength7_Ok() {
        user.setPassword("1234567");
        assertEquals(registration.register(user), user);
    }

    @Test
    void when_passwordLength6_Ok() {
        user.setPassword("123456");
        assertEquals(registration.register(user), user);
    }

    @Test
    void when_passwordLength5_notOk() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void when_loginLength0_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void when_passwordLength0_notOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void when_ageIs18_Ok() {
        user.setAge(18);
        assertEquals(registration.register(user), user);
    }

    @Test
    void when_ageIsNegative5_notOk() {
        user.setAge(-5);
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void when_ageIs17_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void when_ageIs19_Ok() {
        user.setAge(19);
        assertEquals(registration.register(user), user);
    }

    @Test
    void when_ageIs0_notOk() {
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void check_ifUserAdded_Ok() {
        registration.register(user);
        assertEquals(Storage.people.get(0), user);
    }

    @Test
    void when_addExistedUser_notOk() {
        user.setLogin("loginTest");
        registration.register(user);
        user = initUser();
        user.setLogin("loginTest");
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void when_add15ValidUsers_Ok() {
        for (int i = 0; i < 15; ++i) {
            user = initUser();
            user.setLogin("login" + i);
            user.setId(i + 1L);
            registration.register(user);
            peopleCopy.add(user);
        }
        assertTrue(peopleCopy.equals(Storage.people));
    }

    @Test
    void when_15Users_lastMustHaveId15_Ok() {
        for (int i = 0; i < 15; ++i) {
            user = initUser();
            user.setLogin("login" + i);
            registration.register(user);
        }
        assertTrue(15 == Storage.people.get(14).getId());
    }

    private User initUser() {
        User newUser = new User();
        newUser.setId(1L);
        newUser.setAge(20);
        newUser.setLogin("testLogin");
        newUser.setPassword("testPassword");
        return newUser;
    }
}
