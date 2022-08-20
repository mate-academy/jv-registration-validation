package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "userLogin";
    private static final String DEFAULT_PASSWORD = "userPassword";
    private static final int DEFAULT_AGE = 99;
    private static final String WRONG_LOGIN = "";
    private static final String WRONG_PASSWORD = "small";
    private static final int WRONG_AGE = 17;

    private RegistrationService registrationService;
    private User user;
    private User emptyUser;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        emptyUser = new User();
        user = new User();
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
        user.setLogin(DEFAULT_LOGIN);
    }

    @AfterEach
    void clearOldData() {
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
            registrationService.register(emptyUser);
        });
    }

    @Test
    void registration_emptyUserLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_emptyUserPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_emptyUserAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_User_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void registration_reRegistrationUser_notOk() {
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_youngUser_notOk() {
        user.setAge(WRONG_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_smallPassword_notOk() {
        user.setPassword(WRONG_PASSWORD);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_loginLengthZero_notOk() {
        user.setLogin(WRONG_LOGIN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
