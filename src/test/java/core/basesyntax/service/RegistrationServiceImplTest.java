package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User user;
    private static RegistrationService registrationService;
    private static final long ID = 0;
    private static final String LOGIN_VALID = "aboba@gmail.com";
    private static final String PASSWORD_VALID = "123456";
    private static final String LOGIN_INVALID = "aboba";
    private static final String PASSWORD_INVALID = "12345";
    private static final int AGE_VALID = 18;
    private static final int AGE_INVALID = 17;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(ID);
        user.setAge(AGE_VALID);
        user.setLogin(LOGIN_VALID);
        user.setPassword(PASSWORD_VALID);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(AGE_INVALID);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_invalidLogin_notOk() {
        user.setLogin(LOGIN_INVALID);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_invalidPassword_notOk() {
        user.setPassword(PASSWORD_INVALID);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginAlreadyExists_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }
}
