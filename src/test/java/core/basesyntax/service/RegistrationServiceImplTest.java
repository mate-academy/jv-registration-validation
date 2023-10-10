package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final int LOW_USER_AGE = 14;
    private static final int DEFAULT_USER_AGE = 18;
    private static final int NEGATIVE_USER_AGE = -17;
    private static final String DEFAULT_PASSWORD = "psswrd";
    private static final String SHORT_PASSWORD_4 = "pswd";
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User("Victor", DEFAULT_PASSWORD, DEFAULT_USER_AGE);
    }

    @AfterEach
    void clearUp() {
        Storage.people.clear();
    }

    @Test
    void register_userIsAdult_Ok() {
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userAgeUnder18_notOk() {
        user.setAge(LOW_USER_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeUserAge_notOk() {
        user.setAge(NEGATIVE_USER_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserLoginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserPasswordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserLoginExist_notOk() {
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_smallUserPassword_notOk() {
        user.setPassword(SHORT_PASSWORD_4);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
