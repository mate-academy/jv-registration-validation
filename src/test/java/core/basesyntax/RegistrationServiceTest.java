package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final int VALID_USER_AGE = 19;
    private static final String VALID_USER_LOGIN = "MyLongLogin";
    private static final String VALID_USER_PASSWORD = "MyLongPassword";
    private static final int INVALID_USER_AGE = 2;
    private static final String INVALID_USER_LOGIN = "login";
    private static final String INVALID_USER_PASSWORD = "pass";
    private static final int DUPLICATE_VALID_USER_AGE = 20;
    private static final String DUPLICATE_VALID_USER_LOGIN = "LongLogin";
    private static final String DUPLICATE_VALID_USER_PASSWORD = "LongPassword";
    private static final int NEGATIVE_USER_AGE = -10;
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_userValid_Ok() {
        User expected = new User(
                VALID_USER_LOGIN,
                VALID_USER_PASSWORD,
                VALID_USER_AGE);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_userAgeInvalid_notOk() {
        User invalidUser = new User(
                VALID_USER_LOGIN,
                VALID_USER_PASSWORD,
                INVALID_USER_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(invalidUser);
        });
    }

    @Test
    void register_userLoginInvalid_notOk() {
        User invalidUser = new User(
                INVALID_USER_LOGIN,
                VALID_USER_PASSWORD,
                VALID_USER_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(invalidUser);
        });
    }

    @Test
    void register_userPasswordInvalid_notOk() {
        User invalidUser = new User(
                VALID_USER_LOGIN,
                INVALID_USER_PASSWORD,
                VALID_USER_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(invalidUser);
        });
    }

    @Test
    void register_userAlreadyExists_notOk() {
        User duplicateUser = new User(
                DUPLICATE_VALID_USER_LOGIN,
                DUPLICATE_VALID_USER_PASSWORD,
                DUPLICATE_VALID_USER_AGE);
        Storage.people.add(duplicateUser);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(duplicateUser);
        });
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_userLoginIsNull_notOk() {
        User nullLoginUser = new User(
                null,
                VALID_USER_PASSWORD,
                VALID_USER_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullLoginUser);
        });
    }

    @Test
    void register_userPasswordIsNull_noOk() {
        User nullPasswordUser = new User(
                VALID_USER_LOGIN,
                null,
                VALID_USER_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullPasswordUser);
        });
    }

    @Test
    void register_userAgeIsNegative() {
        User negativeAgeUser = new User(
                VALID_USER_LOGIN,
                VALID_USER_PASSWORD,
                NEGATIVE_USER_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(negativeAgeUser);
        });
    }
}
