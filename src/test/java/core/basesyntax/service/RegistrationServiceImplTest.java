package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "my_valid_login";
    private static final String VALID_PASSWORD = "valid#$PASS@!1234";
    private static final int VALID_AGE = 18;

    private static final String NULL_STRING = null;
    private static final int AGE_ZERO = 0;

    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();

        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @Test
    void register_UserRegistered_Ok() {
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_UserIsNull_notOk() {
        assertException(null);
    }

    @Test
    void register_UserAlreadyExists_notOk() {
        Storage.people.add(user);
        assertException(user);
    }

    @Test
    void register_LoginIsNull_notOk() {
        user.setLogin(NULL_STRING);
        assertException(user);
    }

    @Test
    void register_PasswordIsNull_notOk() {
        user.setPassword(NULL_STRING);
        assertException(user);
    }

    @Test
    void register_AgeIsIncorrect_notOk() {
        user.setAge(AGE_ZERO);
        assertException(user);

        user.setAge(-14);
        assertException(user);

        user.setAge(-1);
        assertException(user);

        user.setAge(7);
        assertException(user);

        user.setAge(17);
        assertException(user);
    }

    @Test
    void register_LoginIsIncorrect_notOk() {
        user.setLogin("");
        assertException(user);

        user.setLogin("smth");
        assertException(user);

        user.setLogin("login");
        assertException(user);
    }

    @Test
    void register_PasswordIsIncorrect_notOk() {
        user.setPassword("");
        assertException(user);

        user.setPassword("123");
        assertException(user);

        user.setPassword("1Psa@");
        assertException(user);
    }

    public void assertException(User user) {
        assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(user));
    }
}
