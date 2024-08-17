package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.UserDataInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final int VALID_USER_AGE = 19;
    private static final String VALID_USER_LOGIN = "MyLongLogin";
    private static final String VALID_USER_PASSWORD = "MyLongPassword";
    private static final int INVALID_USER_AGE = 2;
    private static final String INVALID_USER_LOGIN = "login";
    private static final String INVALID_USER_PASSWORD = "pass";
    private static final long USER_ID = 123L;
    private static final int DUPLICATE_VALID_USER_AGE = 20;
    private static final String DUPLICATE_VALID_USER_LOGIN = "LongLogin";
    private static final String DUPLICATE_VALID_USER_PASSWORD = "LongPassword";
    private static final long DUPLICATE_USER_ID = 321L;
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    // All fields are valid
    @Test
    void register_userValid_Ok() {
        User validUser = new User(USER_ID,
                VALID_USER_LOGIN,
                VALID_USER_PASSWORD,
                VALID_USER_AGE);
        User actual = registrationService.register(validUser);
        User expected = validUser;
        assertEquals(expected, actual);
    }

    // Some of the fields are invalid
    @Test
    void register_userAgeInvalid_notOk() {
        User invalidUser = new User(USER_ID,
                VALID_USER_LOGIN,
                VALID_USER_PASSWORD,
                INVALID_USER_AGE);
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(invalidUser);
        });
    }

    @Test
    void register_userLoginInvalid_notOk() {
        User invalidUser = new User(USER_ID,
                INVALID_USER_LOGIN,
                VALID_USER_PASSWORD,
                VALID_USER_AGE);
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(invalidUser);
        });
    }

    @Test
    void register_userPasswordInvalid_notOk() {
        User invalidUser = new User(USER_ID,
                VALID_USER_LOGIN,
                INVALID_USER_PASSWORD,
                VALID_USER_AGE);
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(invalidUser);
        });
    }

    // User already exists
    @Test
    void register_userAlreadyExists_notOk() {
        User duplicateUser = new User(DUPLICATE_USER_ID,
                DUPLICATE_VALID_USER_LOGIN,
                DUPLICATE_VALID_USER_PASSWORD,
                DUPLICATE_VALID_USER_AGE);
        registrationService.register(duplicateUser);
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(duplicateUser);
        });
    }

    // User is null
    @Test
    void register_userIsNull_notOk() {
        User nullUser = null;
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(nullUser);
        });
    }
}
