package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDaoImpl database;
    private static RegistrationService registration;
    private User user;

    @BeforeAll
    static void beforeAll() {
        database = new StorageDaoImpl();
        registration = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_userAgeIsLessThan18_NotOk() {
        user.setAge(17);
        user.setLogin("AgeIsLessThan18");
        user.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void register_userAgeNull_NotOk() {
        user.setAge(null);
        user.setLogin("AgeIsNull");
        user.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void register_userAgeIs18_Ok() {
        user.setAge(18);
        user.setLogin("JustProperAge");
        user.setPassword("123456");
        registration.register(user);
        User expected = user;
        User actual = database.get(user.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_userAgeIsAbove18_Ok() {
        user.setAge(44);
        user.setLogin("ProperAge");
        user.setPassword("123456");
        registration.register(user);
        User expected = user;
        User actual = database.get(user.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_userLoginIsNull_NotOk() {
        user.setAge(18);
        user.setLogin(null);
        user.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
        void register_ProperLogin_Ok() {
        user.setLogin("ProperLogin");
        user.setPassword("123456");
        user.setAge(20);
        registration.register(user);
        User expected = user;
        User actual = database.get(user.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_userLoginDuplicate_NotOk() {
        user.setAge(18);
        user.setLogin("RepeatingLogin");
        user.setPassword("123456");
        User user2 = new User();
        user2.setAge(19);
        user2.setLogin("RepeatingLogin");
        user2.setPassword("12345678");
        registration.register(user);
        assertThrows(RuntimeException.class, () -> registration.register(user2));
    }

    @Test
    void register_userPasswordIsNull_NotOk() {
        user.setAge(18);
        user.setLogin("NullPassword");
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
        void register_userShortPassword_NotOk() {
        user.setAge(18);
        user.setLogin("PasswordTooShort");
        user.setPassword("short");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    void register_userProperPassword_Ok() {
        user.setAge(18);
        user.setPassword("123456");
        user.setLogin("ProperPassword");
        registration.register(user);
        User expected = user;
        User actual = database.get(user.getLogin());
        assertEquals(expected, actual);
    }
}
