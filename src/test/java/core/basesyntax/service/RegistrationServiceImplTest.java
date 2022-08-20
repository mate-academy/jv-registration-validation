package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "userLogin";
    private static final String DEFAULT_PASSWORD = "userPassword";
    private static final int DEFAULT_AGE = 99;
    private static final int WRONG_AGE = 17;
    private static final String WRONG_PASSWORD = "small";

    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        Storage.people.clear();
    }

    @Test
    void registration_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void registration_emptyUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_emptyUserLogin_notOk() {
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_emptyUserPassword_notOk() {
        user.setAge(DEFAULT_AGE);
        user.setLogin(DEFAULT_LOGIN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_emptyUserAge_notOk() {
        user.setPassword(DEFAULT_PASSWORD);
        user.setLogin(DEFAULT_LOGIN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_User_Ok() {
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
        user.setLogin(DEFAULT_LOGIN);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void registration_reRegistrationUser_notOk() {
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
        user.setLogin(DEFAULT_LOGIN);
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_youngUser_notOk() {
        user.setAge(WRONG_AGE);
        user.setPassword(DEFAULT_PASSWORD);
        user.setLogin(DEFAULT_LOGIN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_smallPassword_notOk() {
        user.setAge(DEFAULT_AGE);
        user.setPassword(WRONG_PASSWORD);
        user.setLogin(DEFAULT_LOGIN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
