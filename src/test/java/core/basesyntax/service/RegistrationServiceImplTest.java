package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_NEW_USER_LOGIN = "newUser";
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String INVALID_PASSWORD = "12345";
    private final RegistrationService registrationService;
    private final StorageDao storageDao;
    private final User defaultUser;

    public RegistrationServiceImplTest() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        defaultUser = new User("defaultUser", DEFAULT_PASSWORD,
                RegistrationServiceImpl.MIN_AGE);
    }

    @BeforeEach
    void beforeEach() {
        Storage.people.clear();
        storageDao.add(defaultUser);
        defaultUser.setId(0L);
    }

    @Test
    void registerValidUser_Ok() {
        User user = new User(DEFAULT_NEW_USER_LOGIN, DEFAULT_PASSWORD,
                RegistrationServiceImpl.MIN_AGE);
        assertEquals(registrationService.register(user), storageDao.get(user.getLogin()));
    }

    @Test
    void registerNullUser_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void registerUserWithNullLogin_NotOk() {
        User user = new User(null, DEFAULT_PASSWORD, RegistrationServiceImpl.MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithNullPassword_NotOk() {
        User user = new User(DEFAULT_NEW_USER_LOGIN, null, RegistrationServiceImpl.MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithNullAge() {
        User user = new User(DEFAULT_NEW_USER_LOGIN, DEFAULT_PASSWORD, null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithExistingLogin_NotOk() {
        User user = new User(defaultUser.getLogin(), DEFAULT_PASSWORD,
                RegistrationServiceImpl.MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithInvalidPassword_NotOk() {
        User user = new User(DEFAULT_NEW_USER_LOGIN, INVALID_PASSWORD,
                RegistrationServiceImpl.MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerTooYoungUser_NotOk() {
        User user = new User(DEFAULT_NEW_USER_LOGIN, DEFAULT_PASSWORD,
                RegistrationServiceImpl.MIN_AGE - 1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
