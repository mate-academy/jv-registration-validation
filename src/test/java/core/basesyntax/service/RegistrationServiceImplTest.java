package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static RegistrationService registration;
    private User user;

    @BeforeAll
    static void setUp() {
        registration = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUser() {
        user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(18);

    }

    @Test
    void registration_userLogin_ok() {
        user.setLogin("validLogin");
        assertTrue(user.getLogin().length() > MIN_LENGTH,
                "Login is correct");
    }

    @Test
    void registration_userLogin_notOk() {
        user.setLogin("bad");
        assertTrue(user.getLogin().length() < MIN_LENGTH,
                "Login is not correct");
    }

    @Test
    void registration_userPassword_ok() {
        user.setPassword("validPassword");
        assertTrue(user.getPassword().length() > MIN_LENGTH,
                "Password is correct");
    }

    @Test
    void registration_userPassword_notOk() {
        user.setPassword("bad");
        assertTrue(user.getPassword().length() < MIN_LENGTH,
                "Password is not correct");
    }

    @Test
    void registration_userAge_ok() {
        user.setAge(18);
        assertTrue(user.getAge() >= MIN_AGE,
                "User is old enough");
    }

    @Test
    void registration_userAge_notOk() {
        user.setAge(17);
        assertTrue(user.getAge() < MIN_AGE,
                "User is not old enough");
    }

    @Test
    void registration_nullLogin_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(InvalidDataException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void registration_nullAge_notOk() {
        user.setAge(null);
        Assertions.assertThrows(InvalidDataException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void registration_nullPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(InvalidDataException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void registration_nullUser_notOk() {
        user = null;
        Assertions.assertThrows(NullPointerException.class, () -> {
            registration.register(user);
        });
    }
}
