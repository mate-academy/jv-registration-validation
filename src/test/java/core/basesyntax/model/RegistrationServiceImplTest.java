package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.excepction.UserRegistrationException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User user;
    private static final String VALID_LOGIN = "Volodymyr";
    private static final String INVALID_LOGIN = "abcde";
    private static final int VALID_AGE = 18;
    private static final int INVALID_AGE = 17;
    private static final String VALID_PASSWORD = "Password";
    private static final String IVALID_PASSWORD = "Badpa";

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    }

    @Test
    void nullUser_notOk() {
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void null_age_notOK() {
        user.setAge(null);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void null_login_notOk() {
        user.setLogin(null);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void null_password_notOk() {
        user.setPassword(null);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void invalid_age_notOk() {
        user.setAge(INVALID_AGE);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void invalid_login_notOk() {
        user.setLogin(INVALID_LOGIN);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void invalidPassword() {
        user.setPassword(IVALID_PASSWORD);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void userAlreadyExists() {
        Storage.people.add(user);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(user));

    }
}
