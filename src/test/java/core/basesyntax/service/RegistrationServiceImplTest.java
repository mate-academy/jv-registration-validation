package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final String VALID_LOGIN = "abcdef";
    private static final String VALID_PASSWORD = "123456";
    private static final String LOGIN_LESS_THAN_MIN = "abcde";
    private static final String PASSWORD_LESS_THAN_MIN = "12345";
    private static final int AGE_LESS_THAN_MIN = 15;
    private static RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private final User user = new User(VALID_LOGIN, VALID_PASSWORD, MIN_AGE);

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_loginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginIsMin_notOk() {
        user.setLogin(LOGIN_LESS_THAN_MIN);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsMin_notOk() {
        user.setPassword(PASSWORD_LESS_THAN_MIN);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsMin_notOk() {
        user.setAge(AGE_LESS_THAN_MIN);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_existingUser_notOk() {
        Storage.people.add(user);
        assertThrows(UserRegistrationException.class,() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginIsValid_ok() {
        user.setLogin(VALID_LOGIN);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_passwordIsValid_ok() {
        user.setPassword(VALID_PASSWORD);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_ageIsValid_ok() {
        user.setAge(MIN_AGE);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @AfterEach
    void clear() {
        Storage.people.clear();
    }
}
