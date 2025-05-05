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
    private static final String VALID_LOGIN = "Volodymyr";
    private static final String INVALID_LOGIN = "abcde";
    private static final int VALID_AGE = 18;
    private static final int INVALID_AGE = 17;
    private static final String VALID_PASSWORD = "Password";
    private static final String INVALID_PASSWORD = "Badpa";
    private User user;

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
    void register_nullUser_throwsUserRegistrationException() {
        assertThrows(UserRegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullAge_throwsUserRegistrationException() {
        user.setAge(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_throwsUserRegistrationException() {
        user.setLogin(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_throwsUserRegistrationException() {
        user.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_throwsUserRegistrationException() {
        user.setAge(INVALID_AGE);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidLogin_throwsUserRegistrationException() {
        user.setLogin(INVALID_LOGIN);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidPassword_throwsUserRegistrationException() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAlreadyExists_throwsUserRegistrationException() {
        Storage.people.add(user);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }
}
