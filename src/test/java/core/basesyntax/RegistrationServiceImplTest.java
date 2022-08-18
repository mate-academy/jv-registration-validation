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
    void register_additionUsers_ok() {
        User firstUser = new User();
        firstUser.setLogin("Login");
        firstUser.setPassword("passWord1");
        firstUser.setAge(MIN_AGE);

        User secondUser = new User();
        secondUser.setLogin("login");
        secondUser.setPassword("passWord1");
        secondUser.setAge(MIN_AGE);

        boolean expect = firstUser.equals(registration.register(firstUser));
        assertTrue(expect);
        expect = secondUser.equals(registration.register(secondUser));
        assertTrue(expect);
    }

    @Test
    void register_moreThanMinAge_Ok() {
        User firstUser = new User();
        firstUser.setLogin("Login");
        firstUser.setPassword("passWord1");
        firstUser.setAge(MIN_AGE + 1);

        boolean expect = firstUser.equals(registration.register(firstUser));
        assertTrue(expect);
    }

    @Test
    void register_loginIsEmpty_notOk() {
        User firstUser = new User();
        firstUser.setLogin("");

        assertThrows(RuntimeException.class,
                () -> registration.register(firstUser));
    }

    @Test
    void register_loginContainsWhitespaces_notOk() {
        User firstUser = new User();
        firstUser.setLogin("invalid Login");

        assertThrows(RuntimeException.class,
                () -> registration.register(firstUser));
    }

    @Test
    void register_loginIsNull_notOk() {
        User firstUser = new User();
        firstUser.setLogin(null);

        assertThrows(NullPointerException.class,
                () -> registration.register(firstUser));
    }

    @Test
    void register_passwordWithoutDigits_notOk() {
        User firstUser = new User();
        firstUser.setLogin("Login");
        firstUser.setPassword("passWord");

        assertThrows(RuntimeException.class,
                () -> registration.register(firstUser));
    }

    @Test
    void register_passwordWithWhitespace_notOk() {
        User firstUser = new User();
        firstUser.setLogin("Login");
        firstUser.setPassword("pass Word1");

        assertThrows(RuntimeException.class,
                () -> registration.register(firstUser));
    }

    @Test
    void register_passwordWithoutUppercaseLetter_notOk() {
        User firstUser = new User();
        firstUser.setLogin("Login");
        firstUser.setPassword("password1");

        assertThrows(RuntimeException.class,
                () -> registration.register(firstUser));
    }

    @Test
    void register_shortPassword_notOk() {
        User firstUser = new User();
        firstUser.setLogin("Login");
        firstUser.setPassword("short");

        assertThrows(RuntimeException.class,
                () -> registration.register(firstUser));
    }

    @Test
    void register_passwordIsNull_notOk() {
        User firstUser = new User();
        firstUser.setLogin("Login");
        firstUser.setPassword(null);

        assertThrows(NullPointerException.class,
                () -> registration.register(firstUser));
    }

    @Test
    void register_lessThanMinAge_notOk() {
        User firstUser = new User();
        firstUser.setLogin("Login");
        firstUser.setPassword("passWord1");
        firstUser.setAge(MIN_AGE - 1);

        assertThrows(RuntimeException.class,
                () -> registration.register(firstUser));
    }

    @Test
    void register_ageIsNull_notOk() {
        User firstUser = new User();
        firstUser.setLogin("Login");
        firstUser.setPassword("passWord1");
        firstUser.setAge(null);

        assertThrows(NullPointerException.class,
                () -> registration.register(firstUser));
    }

    @Test
    void register_userWithExistingLogin_notOk() {
        User firstUser = new User();
        firstUser.setLogin("Login");
        firstUser.setPassword("passWord1");
        firstUser.setAge(MIN_AGE);

        registration.register(firstUser);
        assertThrows(RuntimeException.class,
                () -> registration.register(firstUser));
    }
}
