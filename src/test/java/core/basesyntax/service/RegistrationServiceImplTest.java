package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.RegistrationUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int DEFAULT_AGE = 18;
    private static final int LOW_AGE = 16;
    private static final int NEGATIVE_AGE = -1;
    private static final String DEFAULT_PASS = "password";
    private static final String SHORT_PASS = "short";
    private static final String DEFAULT_LOGIN = "Developer";
    private static final String SHORT_LOGIN = "login";
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void createNewRegistrationServiceImpl() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        user = new User(DEFAULT_LOGIN, DEFAULT_PASS, DEFAULT_AGE);
    }

    @Test
    void register_newUser_Ok() {
        user.setLogin(DEFAULT_LOGIN);
        User actual = registrationService.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_Suitable_Age() {
        user.setAge(DEFAULT_AGE);
        User actual = registrationService.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_LowAge_NotOK() {
        user.setAge(LOW_AGE);
        assertThrows(RegistrationUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NegativeAge_NotOK() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_NotOK() {
        user.setLogin(null);
        assertThrows(RegistrationUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_NotOK() {
        user.setAge(null);
        assertThrows(RegistrationUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPass_NotOK() {
        user.setPassword(null);
        assertThrows(RegistrationUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ShortPass_NotOK() {
        user.setPassword(SHORT_PASS);
        assertThrows(RegistrationUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ShortLogin_NotOK() {
        user.setPassword(SHORT_LOGIN);
        assertThrows(RegistrationUserException.class, () -> {
            registrationService.register(user);
        });
    }

}
