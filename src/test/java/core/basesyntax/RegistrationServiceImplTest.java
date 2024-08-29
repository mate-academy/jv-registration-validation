package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_USER_LOGIN_ONE = "DummyUser";
    private static final String DEFAULT_USER_LOGIN_TWO = "DummyUserTwo";
    private static final String DEFAULT_USER_PASSWORD = "DummyUserPassword";
    private static final String SHORT_LOGIN = "Login";
    private static final String SHORT_PASSWORD = "pass";
    private static final int DEFAULT_USER_AGE = 18;
    private StorageDao storageDao = new StorageDaoImpl();
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User dummyUser;

    @BeforeEach
    void setUp() {
        dummyUser = new User();
        dummyUser.setLogin(DEFAULT_USER_LOGIN_ONE);
        dummyUser.setPassword(DEFAULT_USER_PASSWORD);
        dummyUser.setAge(DEFAULT_USER_AGE);
    }

    @Test
    void register_alreadyExists_notOk() {
        storageDao.add(dummyUser);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(dummyUser));
        assertEquals("Login already exists", exception.getMessage());
    }

    @Test
    void register_nullUser_notOk() {
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        dummyUser.setLogin(null);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(dummyUser));
        assertEquals("Login cannot be null", exception.getMessage());
    }

    @Test
    void register_invalidLogin_notOk() {
        dummyUser.setLogin(SHORT_LOGIN);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(dummyUser));
        assertEquals("Login must be at least 6 characters", exception.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        dummyUser.setPassword(null);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(dummyUser));
        assertEquals("Password cannot be null", exception.getMessage());
    }

    @Test
    void register_invalidPassword_notOk() {
        dummyUser.setPassword(SHORT_PASSWORD);
        dummyUser.setLogin(DEFAULT_USER_LOGIN_TWO);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(dummyUser));
        assertEquals("Password must be at least 6 characters", exception.getMessage());
    }

    @Test
    void register_lowAge_notOk() {
        dummyUser.setAge(17);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(dummyUser));
        assertEquals("Age must be at least 18", exception.getMessage());
    }

    @Test
    void register_validUser_Ok() {
        User expectedUser = new User();
        expectedUser.setLogin(DEFAULT_USER_LOGIN_ONE);
        expectedUser.setPassword(DEFAULT_USER_PASSWORD);
        expectedUser.setAge(DEFAULT_USER_AGE);
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
    }
}
