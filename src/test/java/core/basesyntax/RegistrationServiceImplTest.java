package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static RegistrationService registration;

    @BeforeAll
    static void beforeAll() {
        registration = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_moreThanMinAge_Ok() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword("passWord1");
        user.setAge(MIN_AGE + 1);
        boolean expected = user.equals(registration.register(user));
        assertTrue(expected);
    }

    @Test
    void register_loginIsEmpty_notOk() {
        User user = new User();
        user.setLogin("");
        assertThrows(RuntimeException.class,
                () -> registration.register(user));
    }

    @Test
    void register_loginContainsWhitespaces_notOk() {
        User user = new User();
        user.setLogin("invalid Login");
        assertThrows(RuntimeException.class,
                () -> registration.register(user));
    }

    @Test
    void register_loginIsNull_notOk() {
        User user = new User();
        user.setLogin(null);
        assertThrows(NullPointerException.class,
                () -> registration.register(user));
    }

    @Test
    void register_passwordWithoutDigits_notOk() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword("passWord");
        assertThrows(RuntimeException.class,
                () -> registration.register(user));
    }

    @Test
    void register_passwordWithWhitespace_notOk() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword("pass Word1");
        assertThrows(RuntimeException.class,
                () -> registration.register(user));
    }

    @Test
    void register_passwordWithoutUppercaseLetter_notOk() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword("password1");
        assertThrows(RuntimeException.class,
                () -> registration.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword("short");
        assertThrows(RuntimeException.class,
                () -> registration.register(user));
    }

    @Test
    void register_passwordIsNull_notOk() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword(null);
        assertThrows(NullPointerException.class,
                () -> registration.register(user));
    }

    @Test
    void register_lessThanMinAge_notOk() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword("passWord1");
        user.setAge(MIN_AGE - 1);
        assertThrows(RuntimeException.class,
                () -> registration.register(user));
    }

    @Test
    void register_ageIsNull_notOk() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword("passWord1");
        user.setAge(null);
        assertThrows(NullPointerException.class,
                () -> registration.register(user));
    }

    @Test
    void register_userWithExistingLogin_notOk() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword("passWord1");
        user.setAge(MIN_AGE);
        registration.register(user);
        assertThrows(RuntimeException.class,
                () -> registration.register(user));
    }
}
