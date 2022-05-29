package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl register;
    private User user;

    @BeforeAll
    static void beforeAll() {
        register = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("test-user");
        user.setPassword("test-password");
        user.setAge(RegistrationServiceImpl.AGE_MINIMUM_FOR_REGISTRATION);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_loginNull_notOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> register.register(user));
    }

    @Test
    void register_loginAlreadyExist_notOk() {
        register.register(user);
        User newUser = new User();
        newUser.setLogin("test-user");
        newUser.setPassword("test-password");
        newUser.setAge(RegistrationServiceImpl.AGE_MINIMUM_FOR_REGISTRATION);
        assertThrows(RuntimeException.class, () -> register.register(newUser));
    }

    @Test
    void register_loginEmpty_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> register.register(user));
    }

    @Test
    void register_login_Ok() {
        register.register(user);
        User expected = new User();
        expected.setLogin("new-test-user");
        expected.setAge(RegistrationServiceImpl.AGE_MINIMUM_FOR_REGISTRATION);
        expected.setPassword("test-password");
        User actual = register.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_ageNull_notOk() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () -> register.register(user));
    }

    @Test
    void register_age_notOk() {
        user.setAge(RegistrationServiceImpl.AGE_MINIMUM_FOR_REGISTRATION - 1);
        assertThrows(RuntimeException.class, () -> register.register(user));
    }

    @Test
    void register_age_Ok() {
        user.setAge(RegistrationServiceImpl.AGE_MINIMUM_FOR_REGISTRATION);
        User actual = register.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> register.register(user));
    }

    @Test
    void register_passwordEmpty_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> register.register(user));
    }

    @Test
    void register_passwordShort_notOk() {
        user.setPassword("*".repeat(RegistrationServiceImpl.PASSWORD_MINIMUM_LENGTH - 1));
        assertThrows(RuntimeException.class, () -> register.register(user));
    }

    @Test
    void register_password_Ok() {
        user.setPassword("*".repeat(RegistrationServiceImpl.PASSWORD_MINIMUM_LENGTH));
        User actual = register.register(user);
        assertEquals(user, actual);
    }
}
